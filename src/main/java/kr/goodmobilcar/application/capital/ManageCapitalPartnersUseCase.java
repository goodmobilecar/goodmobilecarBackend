package kr.goodmobilcar.application.capital;

import kr.goodmobilcar.domain.capital.CapitalPartner;
import kr.goodmobilcar.domain.capital.CapitalPartnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ManageCapitalPartnersUseCase {

    private final CapitalPartnerRepository repository;

    public ManageCapitalPartnersUseCase(CapitalPartnerRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CapitalPartner> getActive() {
        return repository.findAllActive();
    }

    @Transactional(readOnly = true)
    public List<CapitalPartner> getAll() {
        return repository.findAll();
    }

    @Transactional
    public CapitalPartner create(String name, String logoUrl, int displayOrder) {
        CapitalPartner partner = CapitalPartner.builder()
                .name(name).logoUrl(logoUrl).displayOrder(displayOrder).build();
        return repository.save(partner);
    }

    @Transactional
    public CapitalPartner update(Long id, String name, String logoUrl, Integer displayOrder) {
        CapitalPartner partner = repository.findById(id)
                .orElseThrow(() -> new CapitalPartnerNotFoundException(id));
        CapitalPartner updated = partner.toBuilder()
                .name(name != null ? name : partner.getName())
                .logoUrl(logoUrl != null ? logoUrl : partner.getLogoUrl())
                .displayOrder(displayOrder != null ? displayOrder : partner.getDisplayOrder())
                .build();
        return repository.save(updated);
    }

    @Transactional
    public void deactivate(Long id) {
        CapitalPartner partner = repository.findById(id)
                .orElseThrow(() -> new CapitalPartnerNotFoundException(id));
        repository.save(partner.deactivate());
    }

    public static class CapitalPartnerNotFoundException extends RuntimeException {
        public CapitalPartnerNotFoundException(Long id) { super("캐피탈사를 찾을 수 없습니다: " + id); }
    }
}
