package kr.goodmobilcar.infrastructure.notification;

import kr.goodmobilcar.domain.notification.NotificationMessage;
import kr.goodmobilcar.domain.notification.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// 데모용 — 본개발 시 NhnCloudSmsNotifier로 교체
@Component
public class ConsoleLogNotifier implements Notifier {

    private static final Logger log = LoggerFactory.getLogger(ConsoleLogNotifier.class);

    @Override
    public void notify(NotificationMessage message) {
        log.info("[SMS-DEMO] TO: {} | MSG: {}", message.getRecipientPhone(), message.getContent());
    }
}
