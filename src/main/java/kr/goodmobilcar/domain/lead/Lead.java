package kr.goodmobilcar.domain.lead;

import java.time.Instant;

public class Lead {

    private final Long id;
    private final String customerName;
    private final Phone phone;
    private final String carModel;
    private final int leasePeriod;
    private final IncomeType incomeType;

    private final Channel channel;
    private final String deviceType;
    private final String ipAddress;
    private final String userAgent;
    private final String referrer;
    private final String utmSource;
    private final String utmMedium;
    private final String utmCampaign;
    private final String utmTerm;
    private final String utmContent;

    private final String conversionPage;
    private final String conversionPosition;
    private final String visitorId;
    private final String sessionId;
    private final String originalUrl;
    private final String previousReferrer;

    private final Long assigneeId;
    private final LeadStatus status;
    private final String memo;
    private final Instant contactedAt;
    private final Instant convertedAt;

    private final boolean deleted;
    private final Instant deletedAt;
    private final Long deletedBy;

    private final Instant createdAt;
    private final Instant updatedAt;

    private Lead(Builder builder) {
        this.id = builder.id;
        this.customerName = builder.customerName;
        this.phone = builder.phone;
        this.carModel = builder.carModel;
        this.leasePeriod = builder.leasePeriod;
        this.incomeType = builder.incomeType;
        this.channel = builder.channel;
        this.deviceType = builder.deviceType;
        this.ipAddress = builder.ipAddress;
        this.userAgent = builder.userAgent;
        this.referrer = builder.referrer;
        this.utmSource = builder.utmSource;
        this.utmMedium = builder.utmMedium;
        this.utmCampaign = builder.utmCampaign;
        this.utmTerm = builder.utmTerm;
        this.utmContent = builder.utmContent;
        this.conversionPage = builder.conversionPage;
        this.conversionPosition = builder.conversionPosition;
        this.visitorId = builder.visitorId;
        this.sessionId = builder.sessionId;
        this.originalUrl = builder.originalUrl;
        this.previousReferrer = builder.previousReferrer;
        this.assigneeId = builder.assigneeId;
        this.status = builder.status;
        this.memo = builder.memo;
        this.contactedAt = builder.contactedAt;
        this.convertedAt = builder.convertedAt;
        this.deleted = builder.deleted;
        this.deletedAt = builder.deletedAt;
        this.deletedBy = builder.deletedBy;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public Lead assignTo(Long adminId) {
        return toBuilder().assigneeId(adminId).build();
    }

    public Lead updateStatus(LeadStatus newStatus, String newMemo) {
        return toBuilder()
                .status(newStatus)
                .memo(newMemo)
                .contactedAt(newStatus == LeadStatus.CONTACTED ? Instant.now() : this.contactedAt)
                .convertedAt(newStatus == LeadStatus.CONVERTED ? Instant.now() : this.convertedAt)
                .build();
    }

    public Lead softDelete(Long deletedByAdminId) {
        return toBuilder()
                .deleted(true)
                .deletedAt(Instant.now())
                .deletedBy(deletedByAdminId)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder()
                .id(id).customerName(customerName).phone(phone).carModel(carModel)
                .leasePeriod(leasePeriod).incomeType(incomeType).channel(channel)
                .deviceType(deviceType).ipAddress(ipAddress).userAgent(userAgent)
                .referrer(referrer).utmSource(utmSource).utmMedium(utmMedium)
                .utmCampaign(utmCampaign).utmTerm(utmTerm).utmContent(utmContent)
                .conversionPage(conversionPage).conversionPosition(conversionPosition)
                .visitorId(visitorId).sessionId(sessionId)
                .originalUrl(originalUrl).previousReferrer(previousReferrer)
                .assigneeId(assigneeId).status(status).memo(memo)
                .contactedAt(contactedAt).convertedAt(convertedAt)
                .deleted(deleted).deletedAt(deletedAt).deletedBy(deletedBy)
                .createdAt(createdAt).updatedAt(updatedAt);
    }

    // Getters
    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public Phone getPhone() { return phone; }
    public String getCarModel() { return carModel; }
    public int getLeasePeriod() { return leasePeriod; }
    public IncomeType getIncomeType() { return incomeType; }
    public Channel getChannel() { return channel; }
    public String getDeviceType() { return deviceType; }
    public String getIpAddress() { return ipAddress; }
    public String getUserAgent() { return userAgent; }
    public String getReferrer() { return referrer; }
    public String getUtmSource() { return utmSource; }
    public String getUtmMedium() { return utmMedium; }
    public String getUtmCampaign() { return utmCampaign; }
    public String getUtmTerm() { return utmTerm; }
    public String getUtmContent() { return utmContent; }
    public String getConversionPage() { return conversionPage; }
    public String getConversionPosition() { return conversionPosition; }
    public String getVisitorId() { return visitorId; }
    public String getSessionId() { return sessionId; }
    public String getOriginalUrl() { return originalUrl; }
    public String getPreviousReferrer() { return previousReferrer; }
    public Long getAssigneeId() { return assigneeId; }
    public LeadStatus getStatus() { return status; }
    public String getMemo() { return memo; }
    public Instant getContactedAt() { return contactedAt; }
    public Instant getConvertedAt() { return convertedAt; }
    public boolean isDeleted() { return deleted; }
    public Instant getDeletedAt() { return deletedAt; }
    public Long getDeletedBy() { return deletedBy; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public static class Builder {
        private Long id;
        private String customerName;
        private Phone phone;
        private String carModel;
        private int leasePeriod;
        private IncomeType incomeType;
        private Channel channel;
        private String deviceType;
        private String ipAddress;
        private String userAgent;
        private String referrer;
        private String utmSource;
        private String utmMedium;
        private String utmCampaign;
        private String utmTerm;
        private String utmContent;
        private String conversionPage;
        private String conversionPosition;
        private String visitorId;
        private String sessionId;
        private String originalUrl;
        private String previousReferrer;
        private Long assigneeId;
        private LeadStatus status = LeadStatus.NEW;
        private String memo;
        private Instant contactedAt;
        private Instant convertedAt;
        private boolean deleted = false;
        private Instant deletedAt;
        private Long deletedBy;
        private Instant createdAt;
        private Instant updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder customerName(String v) { this.customerName = v; return this; }
        public Builder phone(Phone v) { this.phone = v; return this; }
        public Builder carModel(String v) { this.carModel = v; return this; }
        public Builder leasePeriod(int v) { this.leasePeriod = v; return this; }
        public Builder incomeType(IncomeType v) { this.incomeType = v; return this; }
        public Builder channel(Channel v) { this.channel = v; return this; }
        public Builder deviceType(String v) { this.deviceType = v; return this; }
        public Builder ipAddress(String v) { this.ipAddress = v; return this; }
        public Builder userAgent(String v) { this.userAgent = v; return this; }
        public Builder referrer(String v) { this.referrer = v; return this; }
        public Builder utmSource(String v) { this.utmSource = v; return this; }
        public Builder utmMedium(String v) { this.utmMedium = v; return this; }
        public Builder utmCampaign(String v) { this.utmCampaign = v; return this; }
        public Builder utmTerm(String v) { this.utmTerm = v; return this; }
        public Builder utmContent(String v) { this.utmContent = v; return this; }
        public Builder conversionPage(String v) { this.conversionPage = v; return this; }
        public Builder conversionPosition(String v) { this.conversionPosition = v; return this; }
        public Builder visitorId(String v) { this.visitorId = v; return this; }
        public Builder sessionId(String v) { this.sessionId = v; return this; }
        public Builder originalUrl(String v) { this.originalUrl = v; return this; }
        public Builder previousReferrer(String v) { this.previousReferrer = v; return this; }
        public Builder assigneeId(Long v) { this.assigneeId = v; return this; }
        public Builder status(LeadStatus v) { this.status = v; return this; }
        public Builder memo(String v) { this.memo = v; return this; }
        public Builder contactedAt(Instant v) { this.contactedAt = v; return this; }
        public Builder convertedAt(Instant v) { this.convertedAt = v; return this; }
        public Builder deleted(boolean v) { this.deleted = v; return this; }
        public Builder deletedAt(Instant v) { this.deletedAt = v; return this; }
        public Builder deletedBy(Long v) { this.deletedBy = v; return this; }
        public Builder createdAt(Instant v) { this.createdAt = v; return this; }
        public Builder updatedAt(Instant v) { this.updatedAt = v; return this; }

        public Lead build() { return new Lead(this); }
    }
}
