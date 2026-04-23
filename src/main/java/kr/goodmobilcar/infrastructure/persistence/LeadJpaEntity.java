package kr.goodmobilcar.infrastructure.persistence;

import jakarta.persistence.*;
import kr.goodmobilcar.domain.lead.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "leads")
public class LeadJpaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String phone;

    @Column(name = "car_model", nullable = false)
    private String carModel;

    @Column(name = "lease_period", nullable = false)
    private int leasePeriod;

    @Column(name = "income_type", nullable = false)
    private String incomeType;

    private String channel;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column(columnDefinition = "TEXT")
    private String referrer;

    @Column(name = "utm_source")
    private String utmSource;

    @Column(name = "utm_medium")
    private String utmMedium;

    @Column(name = "utm_campaign")
    private String utmCampaign;

    @Column(name = "utm_term")
    private String utmTerm;

    @Column(name = "utm_content")
    private String utmContent;

    @Column(name = "conversion_page")
    private String conversionPage;

    @Column(name = "conversion_position")
    private String conversionPosition;

    @Column(name = "visitor_id")
    private String visitorId;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "original_url", columnDefinition = "TEXT")
    private String originalUrl;

    @Column(name = "previous_referrer", columnDefinition = "TEXT")
    private String previousReferrer;

    @Column(name = "assignee_id")
    private Long assigneeId;

    @Column(nullable = false)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Column(name = "contacted_at")
    private Instant contactedAt;

    @Column(name = "converted_at")
    private Instant convertedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    protected LeadJpaEntity() {}

    public static LeadJpaEntity fromDomain(Lead lead) {
        LeadJpaEntity e = new LeadJpaEntity();
        e.id = lead.getId();
        e.customerName = lead.getCustomerName();
        e.phone = lead.getPhone().getValue();
        e.carModel = lead.getCarModel();
        e.leasePeriod = lead.getLeasePeriod();
        e.incomeType = lead.getIncomeType().name();
        e.channel = lead.getChannel() != null ? lead.getChannel().name() : null;
        e.deviceType = lead.getDeviceType();
        e.ipAddress = lead.getIpAddress();
        e.userAgent = lead.getUserAgent();
        e.referrer = lead.getReferrer();
        e.utmSource = lead.getUtmSource();
        e.utmMedium = lead.getUtmMedium();
        e.utmCampaign = lead.getUtmCampaign();
        e.utmTerm = lead.getUtmTerm();
        e.utmContent = lead.getUtmContent();
        e.conversionPage = lead.getConversionPage();
        e.conversionPosition = lead.getConversionPosition();
        e.visitorId = lead.getVisitorId();
        e.sessionId = lead.getSessionId();
        e.originalUrl = lead.getOriginalUrl();
        e.previousReferrer = lead.getPreviousReferrer();
        e.assigneeId = lead.getAssigneeId();
        e.status = lead.getStatus().name();
        e.memo = lead.getMemo();
        e.contactedAt = lead.getContactedAt();
        e.convertedAt = lead.getConvertedAt();
        e.deleted = lead.isDeleted();
        e.deletedAt = lead.getDeletedAt();
        e.deletedBy = lead.getDeletedBy();
        e.createdAt = lead.getCreatedAt();
        e.updatedAt = lead.getUpdatedAt();
        return e;
    }

    public Lead toDomain() {
        return Lead.builder()
                .id(id)
                .customerName(customerName)
                .phone(new Phone(phone))
                .carModel(carModel)
                .leasePeriod(leasePeriod)
                .incomeType(IncomeType.valueOf(incomeType))
                .channel(channel != null ? Channel.valueOf(channel) : null)
                .deviceType(deviceType)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .referrer(referrer)
                .utmSource(utmSource)
                .utmMedium(utmMedium)
                .utmCampaign(utmCampaign)
                .utmTerm(utmTerm)
                .utmContent(utmContent)
                .conversionPage(conversionPage)
                .conversionPosition(conversionPosition)
                .visitorId(visitorId)
                .sessionId(sessionId)
                .originalUrl(originalUrl)
                .previousReferrer(previousReferrer)
                .assigneeId(assigneeId)
                .status(LeadStatus.valueOf(status))
                .memo(memo)
                .contactedAt(contactedAt)
                .convertedAt(convertedAt)
                .deleted(deleted)
                .deletedAt(deletedAt)
                .deletedBy(deletedBy)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
