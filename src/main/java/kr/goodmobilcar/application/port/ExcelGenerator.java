package kr.goodmobilcar.application.port;

import kr.goodmobilcar.domain.lead.Lead;
import java.util.List;

public interface ExcelGenerator {
    byte[] generate(List<Lead> leads);
}
