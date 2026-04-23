package kr.goodmobilcar.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.goodmobilcar.application.lead.*;
import kr.goodmobilcar.presentation.dto.CreateLeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    private final CreateLeadUseCase createLeadUseCase;
    private final VerifyCompletionTokenUseCase verifyCompletionTokenUseCase;

    public LeadController(CreateLeadUseCase createLeadUseCase,
                          VerifyCompletionTokenUseCase verifyCompletionTokenUseCase) {
        this.createLeadUseCase = createLeadUseCase;
        this.verifyCompletionTokenUseCase = verifyCompletionTokenUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createLead(@Valid @RequestBody CreateLeadRequest req,
                                         HttpServletRequest httpReq) {
        CreateLeadCommand command = CreateLeadCommand.builder()
                .customerName(req.customerName())
                .phone(req.phone())
                .carModel(req.carModel())
                .leasePeriod(req.leasePeriod())
                .incomeType(req.incomeType())
                .privacyConsent(Boolean.TRUE.equals(req.privacyConsent()))
                .thirdPartyConsent(Boolean.TRUE.equals(req.thirdPartyConsent()))
                .marketingConsent(Boolean.TRUE.equals(req.marketingConsent()))
                .turnstileToken(req.turnstileToken())
                .utmSource(req.utm() != null ? req.utm().source() : null)
                .utmMedium(req.utm() != null ? req.utm().medium() : null)
                .utmCampaign(req.utm() != null ? req.utm().campaign() : null)
                .utmTerm(req.utm() != null ? req.utm().term() : null)
                .utmContent(req.utm() != null ? req.utm().content() : null)
                .visitorId(req.tracking() != null ? req.tracking().visitorId() : null)
                .sessionId(req.tracking() != null ? req.tracking().sessionId() : null)
                .conversionPage(req.tracking() != null ? req.tracking().conversionPage() : null)
                .conversionPosition(req.tracking() != null ? req.tracking().conversionPosition() : null)
                .ipAddress(getClientIp(httpReq))
                .userAgent(httpReq.getHeader("User-Agent"))
                .originalUrl(httpReq.getHeader("X-Original-URL"))
                .previousReferrer(httpReq.getHeader("X-Previous-Referer"))
                .build();

        CreateLeadResult result = createLeadUseCase.execute(command);

        return ResponseEntity.status(201).body(Map.of(
                "success", true,
                "leadId", result.getLeadId(),
                "completionToken", result.getCompletionToken(),
                "redirectUrl", result.getRedirectUrl(),
                "message", "신청이 완료되었습니다"
        ));
    }

    @PostMapping("/completion/verify")
    public ResponseEntity<?> verifyToken(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        VerifyCompletionTokenUseCase.VerifyResult result = verifyCompletionTokenUseCase.execute(token);
        return ResponseEntity.ok(Map.of(
                "valid", result.valid(),
                "leadId", result.leadId(),
                "alreadyConsumed", result.alreadyConsumed()
        ));
    }

    private String getClientIp(HttpServletRequest req) {
        String xff = req.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) return xff.split(",")[0].trim();
        return req.getRemoteAddr();
    }
}
