package kr.goodmobilcar.application.lead;

public class CreateLeadCommand {

    private final String customerName;
    private final String phone;
    private final String carModel;
    private final int leasePeriod;
    private final String incomeType;
    private final boolean privacyConsent;
    private final boolean thirdPartyConsent;
    private final boolean marketingConsent;
    private final String turnstileToken;

    // UTM
    private final String utmSource;
    private final String utmMedium;
    private final String utmCampaign;
    private final String utmTerm;
    private final String utmContent;

    // 추적
    private final String visitorId;
    private final String sessionId;
    private final String conversionPage;
    private final String conversionPosition;

    // 헤더
    private final String ipAddress;
    private final String userAgent;
    private final String originalUrl;
    private final String previousReferrer;

    private CreateLeadCommand(Builder builder) {
        this.customerName = builder.customerName;
        this.phone = builder.phone;
        this.carModel = builder.carModel;
        this.leasePeriod = builder.leasePeriod;
        this.incomeType = builder.incomeType;
        this.privacyConsent = builder.privacyConsent;
        this.thirdPartyConsent = builder.thirdPartyConsent;
        this.marketingConsent = builder.marketingConsent;
        this.turnstileToken = builder.turnstileToken;
        this.utmSource = builder.utmSource;
        this.utmMedium = builder.utmMedium;
        this.utmCampaign = builder.utmCampaign;
        this.utmTerm = builder.utmTerm;
        this.utmContent = builder.utmContent;
        this.visitorId = builder.visitorId;
        this.sessionId = builder.sessionId;
        this.conversionPage = builder.conversionPage;
        this.conversionPosition = builder.conversionPosition;
        this.ipAddress = builder.ipAddress;
        this.userAgent = builder.userAgent;
        this.originalUrl = builder.originalUrl;
        this.previousReferrer = builder.previousReferrer;
    }

    public String getCustomerName() { return customerName; }
    public String getPhone() { return phone; }
    public String getCarModel() { return carModel; }
    public int getLeasePeriod() { return leasePeriod; }
    public String getIncomeType() { return incomeType; }
    public boolean isPrivacyConsent() { return privacyConsent; }
    public boolean isThirdPartyConsent() { return thirdPartyConsent; }
    public boolean isMarketingConsent() { return marketingConsent; }
    public String getTurnstileToken() { return turnstileToken; }
    public String getUtmSource() { return utmSource; }
    public String getUtmMedium() { return utmMedium; }
    public String getUtmCampaign() { return utmCampaign; }
    public String getUtmTerm() { return utmTerm; }
    public String getUtmContent() { return utmContent; }
    public String getVisitorId() { return visitorId; }
    public String getSessionId() { return sessionId; }
    public String getConversionPage() { return conversionPage; }
    public String getConversionPosition() { return conversionPosition; }
    public String getIpAddress() { return ipAddress; }
    public String getUserAgent() { return userAgent; }
    public String getOriginalUrl() { return originalUrl; }
    public String getPreviousReferrer() { return previousReferrer; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String customerName;
        private String phone;
        private String carModel;
        private int leasePeriod;
        private String incomeType;
        private boolean privacyConsent;
        private boolean thirdPartyConsent;
        private boolean marketingConsent;
        private String turnstileToken;
        private String utmSource;
        private String utmMedium;
        private String utmCampaign;
        private String utmTerm;
        private String utmContent;
        private String visitorId;
        private String sessionId;
        private String conversionPage;
        private String conversionPosition;
        private String ipAddress;
        private String userAgent;
        private String originalUrl;
        private String previousReferrer;

        public Builder customerName(String v) { this.customerName = v; return this; }
        public Builder phone(String v) { this.phone = v; return this; }
        public Builder carModel(String v) { this.carModel = v; return this; }
        public Builder leasePeriod(int v) { this.leasePeriod = v; return this; }
        public Builder incomeType(String v) { this.incomeType = v; return this; }
        public Builder privacyConsent(boolean v) { this.privacyConsent = v; return this; }
        public Builder thirdPartyConsent(boolean v) { this.thirdPartyConsent = v; return this; }
        public Builder marketingConsent(boolean v) { this.marketingConsent = v; return this; }
        public Builder turnstileToken(String v) { this.turnstileToken = v; return this; }
        public Builder utmSource(String v) { this.utmSource = v; return this; }
        public Builder utmMedium(String v) { this.utmMedium = v; return this; }
        public Builder utmCampaign(String v) { this.utmCampaign = v; return this; }
        public Builder utmTerm(String v) { this.utmTerm = v; return this; }
        public Builder utmContent(String v) { this.utmContent = v; return this; }
        public Builder visitorId(String v) { this.visitorId = v; return this; }
        public Builder sessionId(String v) { this.sessionId = v; return this; }
        public Builder conversionPage(String v) { this.conversionPage = v; return this; }
        public Builder conversionPosition(String v) { this.conversionPosition = v; return this; }
        public Builder ipAddress(String v) { this.ipAddress = v; return this; }
        public Builder userAgent(String v) { this.userAgent = v; return this; }
        public Builder originalUrl(String v) { this.originalUrl = v; return this; }
        public Builder previousReferrer(String v) { this.previousReferrer = v; return this; }
        public CreateLeadCommand build() { return new CreateLeadCommand(this); }
    }
}
