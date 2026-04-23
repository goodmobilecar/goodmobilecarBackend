package kr.goodmobilcar.infrastructure.persistence;

import kr.goodmobilcar.domain.capital.CapitalPartner;
import kr.goodmobilcar.domain.capital.CapitalPartnerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CapitalPartnerRepositoryImpl implements CapitalPartnerRepository {

    private final CapitalPartnerJpaRepository jpaRepo;

    public CapitalPartnerRepositoryImpl(CapitalPartnerJpaRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public CapitalPartner save(CapitalPartner partner) {
        return jpaRepo.save(CapitalPartnerJpaEntity.fromDomain(partner)).toDomain();
    }

    @Override
    public Optional<CapitalPartner> findById(Long id) {
        return jpaRepo.findById(id).map(CapitalPartnerJpaEntity::toDomain);
    }

    @Override
    public List<CapitalPartner> findAllActive() {
        return jpaRepo.findByActiveTrueOrderByDisplayOrderAsc()
                .stream().map(CapitalPartnerJpaEntity::toDomain).toList();
    }

    @Override
    public List<CapitalPartner> findAll() {
        return jpaRepo.findAll(
                        org.springframework.data.domain.Sort.by("displayOrder")
                )
                .stream().map(CapitalPartnerJpaEntity::toDomain).toList();
    }
}
