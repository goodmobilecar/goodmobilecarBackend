package kr.goodmobilcar.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompletionTokenJpaRepository extends JpaRepository<CompletionTokenJpaEntity, Long> {
    Optional<CompletionTokenJpaEntity> findByToken(String token);
}
