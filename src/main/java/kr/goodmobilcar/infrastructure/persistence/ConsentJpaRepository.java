package kr.goodmobilcar.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ConsentJpaRepository extends JpaRepository<ConsentJpaEntity, Long> {
    Optional<ConsentJpaEntity> findByLeadId(Long leadId);
}
