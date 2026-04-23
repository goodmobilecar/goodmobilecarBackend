package kr.goodmobilcar.infrastructure.spam;

import kr.goodmobilcar.domain.spam.SpamChecker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class TurnstileSpamChecker implements SpamChecker {

    private final String secretKey;
    private final String verifyUrl;
    private final RestTemplate restTemplate;

    public TurnstileSpamChecker(
            @Value("${app.turnstile.secret-key}") String secretKey,
            @Value("${app.turnstile.verify-url}") String verifyUrl) {
        this.secretKey = secretKey;
        this.verifyUrl = verifyUrl;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public boolean isValid(String token, String remoteIp) {
        if (token == null || token.isBlank()) return false;

        Map<String, String> body = new HashMap<>();
        body.put("secret", secretKey);
        body.put("response", token);
        if (remoteIp != null) body.put("remoteip", remoteIp);

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.postForObject(verifyUrl, body, Map.class);
            return response != null && Boolean.TRUE.equals(response.get("success"));
        } catch (Exception e) {
            return false;
        }
    }
}
