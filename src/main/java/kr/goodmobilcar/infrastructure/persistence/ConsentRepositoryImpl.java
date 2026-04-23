package kr.goodmobilcar.infrastructure.persistence;

import kr.goodmobilcar.domain.consent.Consent;
import kr.goodmobilcar.domain.consent.ConsentRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ConsentRepositoryImpl implements ConsentRepository {

    private final ConsentJpaRepository jpaRepo;

    public ConsentRepositoryImpl(ConsentJpaRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public Consent save(Consent consent) {
        return jpaRepo.save(ConsentJpaEntity.fromDomain(consent)).toDomain();
    }

    @Override
    public Optional<Consent> findByLeadId(Long leadId) {
        return jpaRepo.findByLeadId(leadId).map(ConsentJpaEntity::toDomain);
    }
}
