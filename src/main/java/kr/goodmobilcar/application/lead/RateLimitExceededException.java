package kr.goodmobilcar.application.lead;

public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException() { super("요청 빈도를 초과했습니다"); }
}
