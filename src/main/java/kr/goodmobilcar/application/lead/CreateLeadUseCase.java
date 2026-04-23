package kr.goodmobilcar.application.lead;

import kr.goodmobilcar.domain.admin.Admin;
import kr.goodmobilcar.domain.admin.AdminRepository;
import kr.goodmobilcar.domain.admin.AssignmentStrategy;
import kr.goodmobilcar.domain.consent.Consent;
import kr.goodmobilcar.domain.consent.ConsentRepository;
import kr.goodmobilcar.domain.lead.*;
import kr.goodmobilcar.domain.lead.errors.MissingConsentException;
import kr.goodmobilcar.domain.notification.NotificationMessage;
import kr.goodmobilcar.domain.notification.Notifier;
import kr.goodmobilcar.domain.spam.SpamChecker;
import kr.goodmobilcar.application.port.RateLimiter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class CreateLeadUseCase {

    private static final String POLICY_VERSION = "v1.0";

    private final LeadRepository leadRepository;
    private final ConsentRepository consentRepository;
    private final CompletionTokenRepository completionTokenRepository;
    private final AdminRepository adminRepository;
    private final AssignmentStrategy assignmentStrategy;
    private final Notifier notifier;
    private final SpamChecker spamChecker;
    private final RateLimiter rateLimiter;

    public CreateLeadUseCase(
            LeadRepository leadRepository,
            ConsentRepository consentRepository,
            CompletionTokenRepository completionTokenRepository,
            AdminRepository adminRepository,
            AssignmentStrategy assignmentStrategy,
            Notifier notifier,
            SpamChecker spamChecker,
            RateLimiter rateLimiter) {
        this.leadRepository = leadRepository;
        this.consentRepository = consentRepository;
        this.completionTokenRepository = completionTokenRepository;
        this.adminRepository = adminRepository;
        this.assignmentStrategy = assignmentStrategy;
        this.notifier = notifier;
        this.spamChecker = spamChecker;
        this.rateLimiter = rateLimiter;
    }

    @Transactional
    public CreateLeadResult execute(CreateLeadCommand cmd) {
        // 층 2: Rate Limit
        if (!rateLimiter.isAllowed(cmd.getIpAddress())) {
            throw new RateLimitExceededException();
        }

        // 층 1: Turnstile
        if (!spamChecker.isValid(cmd.getTurnstileToken(), cmd.getIpAddress())) {
            throw new SpamDetectedException();
        }

        // 층 4: 필수 동의
        if (!cmd.isPrivacyConsent() || !cmd.isThirdPartyConsent()) {
            throw new MissingConsentException("필수 동의가 누락되었습니다");
        }

        // 층 5: 24시간 내 중복 체크
        if (leadRepository.existsByPhoneAndCreatedAtAfter(cmd.getPhone(), Instant.now().minusSeconds(86400))) {
            throw new DuplicateLeadException();
        }

        // Lead 생성
        Lead lead = Lead.builder()
                .customerName(cmd.getCustomerName())
                .phone(new Phone(cmd.getPhone()))
                .carModel(cmd.getCarModel())
                .leasePeriod(cmd.getLeasePeriod())
                .incomeType(IncomeType.valueOf(cmd.getIncomeType()))
                .channel(resolveChannel(cmd.getUtmSource()))
                .ipAddress(cmd.getIpAddress())
                .userAgent(cmd.getUserAgent())
                .utmSource(cmd.getUtmSource())
                .utmMedium(cmd.getUtmMedium())
                .utmCampaign(cmd.getUtmCampaign())
                .utmTerm(cmd.getUtmTerm())
                .utmContent(cmd.getUtmContent())
                .visitorId(cmd.getVisitorId())
                .sessionId(cmd.getSessionId())
                .conversionPage(cmd.getConversionPage())
                .conversionPosition(cmd.getConversionPosition())
                .originalUrl(cmd.getOriginalUrl())
                .previousReferrer(cmd.getPreviousReferrer())
                .status(LeadStatus.NEW)
                .build();

        // 담당자 배정
        Lead lastLead = leadRepository.findLastAssigned().orElse(null);
        List<Admin> liveAdmins = adminRepository.findAllLiveOrderByPriority();
        Admin assignee = assignmentStrategy.assignNext(liveAdmins, lastLead);
        if (assignee != null) {
            lead = lead.assignTo(assignee.getId());
        }

        Lead savedLead = leadRepository.save(lead);

        // 동의 기록
        Consent consent = Consent.builder()
                .leadId(savedLead.getId())
                .privacyConsent(cmd.isPrivacyConsent())
                .thirdPartyConsent(cmd.isThirdPartyConsent())
                .marketingConsent(cmd.isMarketingConsent())
                .policyVersion(POLICY_VERSION)
                .ipAddress(cmd.getIpAddress())
                .userAgent(cmd.getUserAgent() != null ? cmd.getUserAgent() : "")
                .build();
        consentRepository.save(consent);

        // 완료 토큰 발급
        CompletionToken token = CompletionToken.issue(savedLead.getId());
        CompletionToken savedToken = completionTokenRepository.save(token);

        // 비동기 알림 (트랜잭션 밖에서 실패해도 리드 저장엔 영향 없음)
        sendNotifications(savedLead, assignee, liveAdmins);

        return new CreateLeadResult(savedLead.getId(), savedToken.getToken());
    }

    private void sendNotifications(Lead lead, Admin assignee, List<Admin> liveAdmins) {
        String msg = String.format("[굿모빌카] 새 문의: %s / %s / %s",
                lead.getCustomerName(), lead.getPhone().getValue(), lead.getCarModel());

        // 배정된 담당자에게 알림
        if (assignee != null && assignee.getPhone() != null) {
            try { notifier.notify(new NotificationMessage(assignee.getPhone(), msg)); }
            catch (Exception ignored) {}
        }

        // 필수 수신자(본사)에게 알림
        liveAdmins.stream()
                .filter(Admin::isMustReceive)
                .filter(a -> a.getPhone() != null)
                .filter(a -> assignee == null || !a.getId().equals(assignee.getId()))
                .forEach(a -> {
                    try { notifier.notify(new NotificationMessage(a.getPhone(), msg)); }
                    catch (Exception ignored) {}
                });
    }

    private Channel resolveChannel(String utmSource) {
        if (utmSource == null) return Channel.ORGANIC;
        return switch (utmSource.toLowerCase()) {
            case "karrot", "daangn" -> Channel.KARROT;
            case "facebook", "instagram", "meta" -> Channel.META;
            case "google" -> Channel.GOOGLE;
            default -> Channel.ETC;
        };
    }
}
