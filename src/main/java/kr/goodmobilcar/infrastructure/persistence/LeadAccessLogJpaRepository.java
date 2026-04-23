package kr.goodmobilcar.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadAccessLogJpaRepository extends JpaRepository<LeadAccessLogJpaEntity, Long> {
}
