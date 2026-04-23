package kr.goodmobilcar.application.lead;

import kr.goodmobilcar.application.port.ExcelGenerator;
import kr.goodmobilcar.domain.lead.Lead;
import kr.goodmobilcar.domain.lead.LeadRepository;
import kr.goodmobilcar.domain.lead.LeadSearchCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExportLeadsUseCase {

    private final LeadRepository leadRepository;
    private final ExcelGenerator excelGenerator;

    public ExportLeadsUseCase(LeadRepository leadRepository, ExcelGenerator excelGenerator) {
        this.leadRepository = leadRepository;
        this.excelGenerator = excelGenerator;
    }

    @Transactional(readOnly = true)
    public byte[] execute(LeadSearchCondition condition) {
        // 엑셀 다운로드는 페이지네이션 없이 전체 조회
        LeadSearchCondition fullCondition = LeadSearchCondition.builder()
                .dateFrom(condition.getDateFrom())
                .dateTo(condition.getDateTo())
                .channel(condition.getChannel())
                .status(condition.getStatus())
                .page(0)
                .size(Integer.MAX_VALUE)
                .build();
        List<Lead> leads = leadRepository.findAll(fullCondition);
        return excelGenerator.generate(leads);
    }
}
