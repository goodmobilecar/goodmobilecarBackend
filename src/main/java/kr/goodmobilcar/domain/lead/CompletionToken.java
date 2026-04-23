package kr.goodmobilcar.domain.lead;

import java.time.Instant;
import java.util.UUID;

public class CompletionToken {

    private final Long id;
    private final String token;
    private final Long leadId;
    private final boolean consumed;
    private final Instant consumedAt;
    private final Instant expiresAt;
    private final Instant createdAt;

    private CompletionToken(Builder builder) {
        this.id = builder.id;
        this.token = builder.token;
        this.leadId = builder.leadId;
        this.consumed = builder.consumed;
        this.consumedAt = builder.consumedAt;
        this.expiresAt = builder.expiresAt;
        this.createdAt = builder.createdAt;
    }

    public static CompletionToken issue(Long leadId) {
        return new Builder()
                .token("ct_" + UUID.randomUUID().toString().replace("-", ""))
                .leadId(leadId)
                .consumed(false)
                .expiresAt(Instant.now().plusSeconds(86400)) // 24시간
                .createdAt(Instant.now())
                .build();
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public CompletionToken consume() {
        return toBuilder().consumed(true).consumedAt(Instant.now()).build();
    }

    public Long getId() { return id; }
    public String getToken() { return token; }
    public Long getLeadId() { return leadId; }
    public boolean isConsumed() { return consumed; }
    public Instant getConsumedAt() { return consumedAt; }
    public Instant getExpiresAt() { return expiresAt; }
    public Instant getCreatedAt() { return createdAt; }

    public static Builder builder() { return new Builder(); }

    public Builder toBuilder() {
        return new Builder().id(id).token(token).leadId(leadId)
                .consumed(consumed).consumedAt(consumedAt)
                .expiresAt(expiresAt).createdAt(createdAt);
    }

    public static class Builder {
        private Long id;
        private String token;
        private Long leadId;
        private boolean consumed = false;
        private Instant consumedAt;
        private Instant expiresAt;
        private Instant createdAt;

        public Builder id(Long v) { this.id = v; return this; }
        public Builder token(String v) { this.token = v; return this; }
        public Builder leadId(Long v) { this.leadId = v; return this; }
        public Builder consumed(boolean v) { this.consumed = v; return this; }
        public Builder consumedAt(Instant v) { this.consumedAt = v; return this; }
        public Builder expiresAt(Instant v) { this.expiresAt = v; return this; }
        public Builder createdAt(Instant v) { this.createdAt = v; return this; }
        public CompletionToken build() { return new CompletionToken(this); }
    }
}
