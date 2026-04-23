package kr.goodmobilcar.application.lead;

import kr.goodmobilcar.domain.lead.Lead;
import kr.goodmobilcar.domain.lead.LeadRepository;
import kr.goodmobilcar.domain.lead.LeadStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateLeadUseCase {

    private final LeadRepository leadRepository;

    public UpdateLeadUseCase(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @Transactional
    public Lead execute(Long leadId, LeadStatus status, String memo) {
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new LeadNotFoundException(leadId));
        Lead updated = lead.updateStatus(status, memo);
        return leadRepository.save(updated);
    }
}
