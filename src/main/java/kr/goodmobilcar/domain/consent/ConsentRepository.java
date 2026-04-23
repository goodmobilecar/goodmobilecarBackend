package kr.goodmobilcar.domain.consent;

import java.util.Optional;

public interface ConsentRepository {
    Consent save(Consent consent);
    Optional<Consent> findByLeadId(Long leadId);
}
