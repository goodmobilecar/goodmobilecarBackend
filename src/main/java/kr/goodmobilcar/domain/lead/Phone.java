package kr.goodmobilcar.domain.lead;

import kr.goodmobilcar.domain.lead.errors.InvalidPhoneException;

public class Phone {

    private static final String PATTERN = "^01[0-9]-\\d{3,4}-\\d{4}$";

    private final String value;

    public Phone(String value) {
        if (value == null || !value.matches(PATTERN)) {
            throw new InvalidPhoneException("전화번호 형식이 올바르지 않습니다: " + value);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
