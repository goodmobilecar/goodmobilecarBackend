package kr.goodmobilcar.domain.lead.errors;

public class MissingConsentException extends RuntimeException {
    public MissingConsentException(String message) {
        super(message);
    }
}
