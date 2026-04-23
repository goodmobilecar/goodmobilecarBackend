package kr.goodmobilcar.presentation.aop;

import kr.goodmobilcar.infrastructure.persistence.LeadAccessLogJpaEntity;
import kr.goodmobilcar.infrastructure.persistence.LeadAccessLogJpaRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Aspect
@Component
public class LeadAccessLogAspect {

    private final LeadAccessLogJpaRepository logRepo;

    public LeadAccessLogAspect(LeadAccessLogJpaRepository logRepo) {
        this.logRepo = logRepo;
    }

    @AfterReturning("execution(* kr.goodmobilcar.application.lead.GetLeadsUseCase.findById(..))")
    public void logView(JoinPoint jp) {
        Long leadId = (Long) jp.getArgs()[0];
        saveLog(leadId, "VIEW");
    }

    @AfterReturning("execution(* kr.goodmobilcar.application.lead.UpdateLeadUseCase.execute(..))")
    public void logEdit(JoinPoint jp) {
        Long leadId = (Long) jp.getArgs()[0];
        saveLog(leadId, "EDIT");
    }

    @AfterReturning("execution(* kr.goodmobilcar.application.lead.DeleteLeadUseCase.execute(..))")
    public void logDelete(JoinPoint jp) {
        Long leadId = (Long) jp.getArgs()[0];
        saveLog(leadId, "DELETE");
    }

    @AfterReturning("execution(* kr.goodmobilcar.application.lead.ExportLeadsUseCase.execute(..))")
    public void logExport(JoinPoint jp) {
        saveLog(null, "EXPORT");
    }

    private void saveLog(Long leadId, String action) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Long adminId = auth != null ? (Long) auth.getPrincipal() : null;
            LeadAccessLogJpaEntity log = new LeadAccessLogJpaEntity(adminId, leadId, action, Instant.now());
            logRepo.save(log);
        } catch (Exception ignored) {}
    }
}
