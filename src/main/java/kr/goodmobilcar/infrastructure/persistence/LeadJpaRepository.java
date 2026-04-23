package kr.goodmobilcar.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LeadJpaRepository extends JpaRepository<LeadJpaEntity, Long>,
        JpaSpecificationExecutor<LeadJpaEntity> {
}
