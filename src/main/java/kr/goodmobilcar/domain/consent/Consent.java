package kr.goodmobilcar.domain.consent;

import java.time.Instant;

public class Consent {

    private final Long id;
    private final Long leadId;
    private final boolean privacyConsent;
    private final boolean thirdPartyConsent;
    private final boolean marketingConsent;
    private final String policyVersion;
    private final String ipAddress;
    private final String userAgent;
    private final Instant agreedAt;
    private final Instant revokedAt;
    private final String revokeReason;

    private Consent(Builder builder) {
        this.id = builder.id;
        this.leadId = builder.leadId;
        this.privacyConsent = builder.privacyConsent;
        this.thirdPartyConsent = builder.thirdPartyConsent;
        this.marketingConsent = builder.marketingConsent;
        this.policyVersion = builder.policyVersion;
        this.ipAddress = builder.ipAddress;
        this.userAgent = builder.userAgent;
        this.agreedAt = builder.agreedAt;
        this.revokedAt = builder.revokedAt;
        this.revokeReason = builder.revokeReason;
    }

    public boolean isRequiredConsentsGiven() {
        return privacyConsent && thirdPartyConsent;
    }

    public Long getId() { return id; }
    public Long getLeadId() { return leadId; }
    public boolean isPrivacyConsent() { return privacyConsent; }
    public boolean isThirdPartyConsent() { return thirdPartyConsent; }
    public boolean isMarketingConsent() { return marketingConsent; }
    public String getPolicyVersion() { return policyVersion; }
    public String getIpAddress() { return ipAddress; }
    public String getUserAgent() { return userAgent; }
    public Instant getAgreedAt() { return agreedAt; }
    public Instant getRevokedAt() { return revokedAt; }
    public String getRevokeReason() { return revokeReason; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long leadId;
        private boolean privacyConsent;
        private boolean thirdPartyConsent;
        private boolean marketingConsent;
        private String policyVersion;
        private String ipAddress;
        private String userAgent;
        private Instant agreedAt = Instant.now();
        private Instant revokedAt;
        private String revokeReason;

        public Builder id(Long v) { this.id = v; return this; }
        public Builder leadId(Long v) { this.leadId = v; return this; }
        public Builder privacyConsent(boolean v) { this.privacyConsent = v; return this; }
        public Builder thirdPartyConsent(boolean v) { this.thirdPartyConsent = v; return this; }
        public Builder marketingConsent(boolean v) { this.marketingConsent = v; return this; }
        public Builder policyVersion(String v) { this.policyVersion = v; return this; }
        public Builder ipAddress(String v) { this.ipAddress = v; return this; }
        public Builder userAgent(String v) { this.userAgent = v; return this; }
        public Builder agreedAt(Instant v) { this.agreedAt = v; return this; }
        public Builder revokedAt(Instant v) { this.revokedAt = v; return this; }
        public Builder revokeReason(String v) { this.revokeReason = v; return this; }
        public Consent build() { return new Consent(this); }
    }
}
