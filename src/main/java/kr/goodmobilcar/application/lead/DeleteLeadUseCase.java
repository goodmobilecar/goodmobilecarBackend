package kr.goodmobilcar.application.lead;

import kr.goodmobilcar.domain.lead.Lead;
import kr.goodmobilcar.domain.lead.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteLeadUseCase {

    private final LeadRepository leadRepository;

    public DeleteLeadUseCase(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @Transactional
    public void execute(Long leadId, Long deletedByAdminId) {
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new LeadNotFoundException(leadId));
        leadRepository.save(lead.softDelete(deletedByAdminId));
    }
}
