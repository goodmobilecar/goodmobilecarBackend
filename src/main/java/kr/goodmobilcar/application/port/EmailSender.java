package kr.goodmobilcar.application.port;

public interface EmailSender {
    void send(String to, String subject, String body);
}
