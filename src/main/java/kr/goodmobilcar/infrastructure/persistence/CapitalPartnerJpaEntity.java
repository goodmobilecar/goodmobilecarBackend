package kr.goodmobilcar.infrastructure.persistence;

import jakarta.persistence.*;
import kr.goodmobilcar.domain.capital.CapitalPartner;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "capital_partners")
public class CapitalPartnerJpaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    @Column(name = "is_active")
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    protected CapitalPartnerJpaEntity() {}

    public static CapitalPartnerJpaEntity fromDomain(CapitalPartner p) {
        CapitalPartnerJpaEntity e = new CapitalPartnerJpaEntity();
        e.id = p.getId();
        e.name = p.getName();
        e.logoUrl = p.getLogoUrl();
        e.displayOrder = p.getDisplayOrder();
        e.active = p.isActive();
        e.createdAt = p.getCreatedAt();
        e.updatedAt = p.getUpdatedAt();
        return e;
    }

    public CapitalPartner toDomain() {
        return CapitalPartner.builder()
                .id(id).name(name).logoUrl(logoUrl)
                .displayOrder(displayOrder).active(active)
                .createdAt(createdAt).updatedAt(updatedAt)
                .build();
    }
}
