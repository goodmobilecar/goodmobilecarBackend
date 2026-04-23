package kr.goodmobilcar.infrastructure.persistence;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "lead_access_logs")
public class LeadAccessLogJpaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "lead_id")
    private Long leadId;

    @Column(nullable = false)
    private String action;

    @Column(name = "created_at")
    private Instant createdAt;

    protected LeadAccessLogJpaEntity() {}

    public LeadAccessLogJpaEntity(Long adminId, Long leadId, String action, Instant createdAt) {
        this.adminId = adminId;
        this.leadId = leadId;
        this.action = action;
        this.createdAt = createdAt;
    }
}
