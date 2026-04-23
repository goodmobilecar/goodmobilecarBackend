package kr.goodmobilcar.domain.spam;

public interface SpamChecker {
    boolean isValid(String token, String remoteIp);
}
