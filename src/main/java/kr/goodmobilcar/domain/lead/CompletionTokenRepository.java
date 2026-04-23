package kr.goodmobilcar.domain.lead;

import java.util.Optional;

public interface CompletionTokenRepository {
    CompletionToken save(CompletionToken token);
    Optional<CompletionToken> findByToken(String token);
}
