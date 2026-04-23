package kr.goodmobilcar.infrastructure.persistence;

import jakarta.persistence.*;
import kr.goodmobilcar.domain.consent.Consent;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "consents")
public class ConsentJpaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lead_id")
    private Long leadId;

    @Column(name = "privacy_consent", nullable = false)
    private boolean privacyConsent;

    @Column(name = "third_party_consent", nullable = false)
    private boolean thirdPartyConsent;

    @Column(name = "marketing_consent")
    private boolean marketingConsent;

    @Column(name = "policy_version", nullable = false)
    private String policyVersion;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "user_agent", nullable = false, columnDefinition = "TEXT")
    private String userAgent;

    @CreationTimestamp
    @Column(name = "agreed_at", updatable = false)
    private Instant agreedAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    @Column(name = "revoke_reason")
    private String revokeReason;

    protected ConsentJpaEntity() {}

    public static ConsentJpaEntity fromDomain(Consent c) {
        ConsentJpaEntity e = new ConsentJpaEntity();
        e.id = c.getId();
        e.leadId = c.getLeadId();
        e.privacyConsent = c.isPrivacyConsent();
        e.thirdPartyConsent = c.isThirdPartyConsent();
        e.marketingConsent = c.isMarketingConsent();
        e.policyVersion = c.getPolicyVersion();
        e.ipAddress = c.getIpAddress();
        e.userAgent = c.getUserAgent();
        e.agreedAt = c.getAgreedAt();
        e.revokedAt = c.getRevokedAt();
        e.revokeReason = c.getRevokeReason();
        return e;
    }

    public Consent toDomain() {
        return Consent.builder()
                .id(id).leadId(leadId)
                .privacyConsent(privacyConsent).thirdPartyConsent(thirdPartyConsent)
                .marketingConsent(marketingConsent).policyVersion(policyVersion)
                .ipAddress(ipAddress).userAgent(userAgent)
                .agreedAt(agreedAt).revokedAt(revokedAt).revokeReason(revokeReason)
                .build();
    }
}
