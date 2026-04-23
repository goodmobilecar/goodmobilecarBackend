package kr.goodmobilcar.application.lead;

public class DuplicateLeadException extends RuntimeException {
    public DuplicateLeadException() { super("24시간 내 동일 번호로 이미 신청되었습니다"); }
}
