package kr.goodmobilcar.application.lead;

import kr.goodmobilcar.domain.lead.CompletionToken;
import kr.goodmobilcar.domain.lead.CompletionTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VerifyCompletionTokenUseCase {

    private final CompletionTokenRepository completionTokenRepository;

    public VerifyCompletionTokenUseCase(CompletionTokenRepository completionTokenRepository) {
        this.completionTokenRepository = completionTokenRepository;
    }

    @Transactional
    public VerifyResult execute(String token) {
        CompletionToken ct = completionTokenRepository.findByToken(token)
                .orElseThrow(TokenNotFoundException::new);

        if (ct.isExpired()) {
            throw new TokenExpiredException();
        }

        boolean alreadyConsumed = ct.isConsumed();
        if (!alreadyConsumed) {
            completionTokenRepository.save(ct.consume());
        }

        return new VerifyResult(true, ct.getLeadId(), alreadyConsumed);
    }

    public record VerifyResult(boolean valid, Long leadId, boolean alreadyConsumed) {}

    public static class TokenNotFoundException extends RuntimeException {
        public TokenNotFoundException() { super("토큰을 찾을 수 없습니다"); }
    }

    public static class TokenExpiredException extends RuntimeException {
        public TokenExpiredException() { super("만료된 토큰입니다"); }
    }
}
