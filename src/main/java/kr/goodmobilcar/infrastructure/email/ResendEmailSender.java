package kr.goodmobilcar.infrastructure.email;

import kr.goodmobilcar.application.port.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ResendEmailSender implements EmailSender {

    private static final Logger log = LoggerFactory.getLogger(ResendEmailSender.class);
    private static final String RESEND_URL = "https://api.resend.com/emails";

    private final String apiKey;
    private final String from;
    private final RestTemplate restTemplate;

    public ResendEmailSender(
            @Value("${app.resend.api-key}") String apiKey,
            @Value("${app.resend.from}") String from) {
        this.apiKey = apiKey;
        this.from = from;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void send(String to, String subject, String body) {
        if (apiKey.startsWith("re_dev")) {
            log.info("[EMAIL-DEMO] TO: {} | SUBJECT: {}", to, subject);
            return;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            Map<String, Object> payload = Map.of(
                    "from", from,
                    "to", new String[]{to},
                    "subject", subject,
                    "text", body
            );

            restTemplate.exchange(RESEND_URL, HttpMethod.POST,
                    new HttpEntity<>(payload, headers), String.class);
        } catch (Exception e) {
            log.error("이메일 발송 실패: to={}, error={}", to, e.getMessage());
        }
    }
}
