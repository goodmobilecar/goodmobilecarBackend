package kr.goodmobilcar.presentation.exception;

import kr.goodmobilcar.application.admin.LoginUseCase;
import kr.goodmobilcar.application.admin.UpdateMemberUseCase;
import kr.goodmobilcar.application.capital.ManageCapitalPartnersUseCase;
import kr.goodmobilcar.application.lead.*;
import kr.goodmobilcar.domain.lead.errors.InvalidPhoneException;
import kr.goodmobilcar.domain.lead.errors.MissingConsentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e) {
        return error(400, "VALIDATION_ERROR", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(InvalidPhoneException.class)
    public ResponseEntity<?> handleInvalidPhone(InvalidPhoneException e) {
        return error(400, "INVALID_PHONE", e.getMessage());
    }

    @ExceptionHandler(MissingConsentException.class)
    public ResponseEntity<?> handleMissingConsent(MissingConsentException e) {
        return error(400, "MISSING_REQUIRED_CONSENT", e.getMessage());
    }

    @ExceptionHandler(SpamDetectedException.class)
    public ResponseEntity<?> handleSpam(SpamDetectedException e) {
        return error(403, "SPAM_DETECTED", e.getMessage());
    }

    @ExceptionHandler(DuplicateLeadException.class)
    public ResponseEntity<?> handleDuplicate(DuplicateLeadException e) {
        return error(409, "DUPLICATE_LEAD", e.getMessage());
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<?> handleRateLimit(RateLimitExceededException e) {
        return error(429, "RATE_LIMIT_EXCEEDED", e.getMessage());
    }

    @ExceptionHandler(LeadNotFoundException.class)
    public ResponseEntity<?> handleLeadNotFound(LeadNotFoundException e) {
        return error(404, "NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(VerifyCompletionTokenUseCase.TokenNotFoundException.class)
    public ResponseEntity<?> handleTokenNotFound(VerifyCompletionTokenUseCase.TokenNotFoundException e) {
        return error(404, "TOKEN_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(VerifyCompletionTokenUseCase.TokenExpiredException.class)
    public ResponseEntity<?> handleTokenExpired(VerifyCompletionTokenUseCase.TokenExpiredException e) {
        return error(410, "TOKEN_EXPIRED", e.getMessage());
    }

    @ExceptionHandler(LoginUseCase.InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentials(LoginUseCase.InvalidCredentialsException e) {
        return error(401, "INVALID_CREDENTIALS", e.getMessage());
    }

    @ExceptionHandler(UpdateMemberUseCase.AdminNotFoundException.class)
    public ResponseEntity<?> handleAdminNotFound(UpdateMemberUseCase.AdminNotFoundException e) {
        return error(404, "NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(ManageCapitalPartnersUseCase.CapitalPartnerNotFoundException.class)
    public ResponseEntity<?> handleCapitalNotFound(ManageCapitalPartnersUseCase.CapitalPartnerNotFoundException e) {
        return error(404, "NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception e) {
        return error(500, "INTERNAL_ERROR", "서버 오류가 발생했습니다");
    }

    private ResponseEntity<?> error(int status, String code, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "success", false,
                "error", code,
                "message", message
        ));
    }
}
