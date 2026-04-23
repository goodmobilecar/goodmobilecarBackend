package kr.goodmobilcar.domain.notification;

public class NotificationMessage {

    private final String recipientPhone;
    private final String content;

    public NotificationMessage(String recipientPhone, String content) {
        this.recipientPhone = recipientPhone;
        this.content = content;
    }

    public String getRecipientPhone() { return recipientPhone; }
    public String getContent() { return content; }
}
