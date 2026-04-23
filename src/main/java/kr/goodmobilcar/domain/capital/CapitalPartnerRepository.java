package kr.goodmobilcar.domain.capital;

import java.util.List;
import java.util.Optional;

public interface CapitalPartnerRepository {
    CapitalPartner save(CapitalPartner partner);
    Optional<CapitalPartner> findById(Long id);
    List<CapitalPartner> findAllActive();
    List<CapitalPartner> findAll();
}
