package kr.goodmobilcar.infrastructure.persistence;

import kr.goodmobilcar.domain.lead.CompletionToken;
import kr.goodmobilcar.domain.lead.CompletionTokenRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CompletionTokenRepositoryImpl implements CompletionTokenRepository {

    private final CompletionTokenJpaRepository jpaRepo;

    public CompletionTokenRepositoryImpl(CompletionTokenJpaRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public CompletionToken save(CompletionToken token) {
        return jpaRepo.save(CompletionTokenJpaEntity.fromDomain(token)).toDomain();
    }

    @Override
    public Optional<CompletionToken> findByToken(String token) {
        return jpaRepo.findByToken(token).map(CompletionTokenJpaEntity::toDomain);
    }
}
