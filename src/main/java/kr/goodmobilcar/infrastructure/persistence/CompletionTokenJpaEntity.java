package kr.goodmobilcar.infrastructure.persistence;

import jakarta.persistence.*;
import kr.goodmobilcar.domain.lead.CompletionToken;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "completion_tokens")
public class CompletionTokenJpaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(name = "lead_id")
    private Long leadId;

    @Column(nullable = false)
    private boolean consumed = false;

    @Column(name = "consumed_at")
    private Instant consumedAt;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    protected CompletionTokenJpaEntity() {}

    public static CompletionTokenJpaEntity fromDomain(CompletionToken t) {
        CompletionTokenJpaEntity e = new CompletionTokenJpaEntity();
        e.id = t.getId();
        e.token = t.getToken();
        e.leadId = t.getLeadId();
        e.consumed = t.isConsumed();
        e.consumedAt = t.getConsumedAt();
        e.expiresAt = t.getExpiresAt();
        e.createdAt = t.getCreatedAt();
        return e;
    }

    public CompletionToken toDomain() {
        return CompletionToken.builder()
                .id(id).token(token).leadId(leadId)
                .consumed(consumed).consumedAt(consumedAt)
                .expiresAt(expiresAt).createdAt(createdAt)
                .build();
    }
}
