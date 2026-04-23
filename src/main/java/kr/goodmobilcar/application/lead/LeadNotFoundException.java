package kr.goodmobilcar.application.lead;

public class LeadNotFoundException extends RuntimeException {
    public LeadNotFoundException(Long id) { super("리드를 찾을 수 없습니다: " + id); }
}
