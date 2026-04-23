package kr.goodmobilcar.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface AdminJpaRepository extends JpaRepository<AdminJpaEntity, Long>,
        JpaSpecificationExecutor<AdminJpaEntity> {
    Optional<AdminJpaEntity> findByEmail(String email);
}
