package kr.goodmobilcar.application.lead;

import kr.goodmobilcar.domain.lead.Lead;
import kr.goodmobilcar.domain.lead.LeadRepository;
import kr.goodmobilcar.domain.lead.LeadSearchCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetLeadsUseCase {

    private final LeadRepository leadRepository;

    public GetLeadsUseCase(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @Transactional(readOnly = true)
    public LeadPage execute(LeadSearchCondition condition) {
        List<Lead> leads = leadRepository.findAll(condition);
        long total = leadRepository.countAll(condition);
        return new LeadPage(leads, total, condition.getPage(), condition.getSize());
    }

    @Transactional(readOnly = true)
    public Lead findById(Long id) {
        return leadRepository.findById(id)
                .orElseThrow(() -> new LeadNotFoundException(id));
    }

    public record LeadPage(List<Lead> leads, long total, int page, int size) {}
}
