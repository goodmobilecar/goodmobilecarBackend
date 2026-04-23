package kr.goodmobilcar.application.lead;

public class SpamDetectedException extends RuntimeException {
    public SpamDetectedException() { super("스팸 방어에 의해 차단되었습니다"); }
}
