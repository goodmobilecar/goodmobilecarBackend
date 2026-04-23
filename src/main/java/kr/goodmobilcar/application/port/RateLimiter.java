package kr.goodmobilcar.application.port;

public interface RateLimiter {
    boolean isAllowed(String key);
}
