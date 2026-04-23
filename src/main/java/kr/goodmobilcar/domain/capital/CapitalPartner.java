package kr.goodmobilcar.domain.capital;

import java.time.Instant;

public class CapitalPartner {

    private final Long id;
    private final String name;
    private final String logoUrl;
    private final int displayOrder;
    private final boolean active;
    private final Instant createdAt;
    private final Instant updatedAt;

    private CapitalPartner(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.logoUrl = builder.logoUrl;
        this.displayOrder = builder.displayOrder;
        this.active = builder.active;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public CapitalPartner deactivate() {
        return toBuilder().active(false).build();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getLogoUrl() { return logoUrl; }
    public int getDisplayOrder() { return displayOrder; }
    public boolean isActive() { return active; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public static Builder builder() { return new Builder(); }

    public Builder toBuilder() {
        return new Builder().id(id).name(name).logoUrl(logoUrl)
                .displayOrder(displayOrder).active(active)
                .createdAt(createdAt).updatedAt(updatedAt);
    }

    public static class Builder {
        private Long id;
        private String name;
        private String logoUrl;
        private int displayOrder;
        private boolean active = true;
        private Instant createdAt;
        private Instant updatedAt;

        public Builder id(Long v) { this.id = v; return this; }
        public Builder name(String v) { this.name = v; return this; }
        public Builder logoUrl(String v) { this.logoUrl = v; return this; }
        public Builder displayOrder(int v) { this.displayOrder = v; return this; }
        public Builder active(boolean v) { this.active = v; return this; }
        public Builder createdAt(Instant v) { this.createdAt = v; return this; }
        public Builder updatedAt(Instant v) { this.updatedAt = v; return this; }
        public CapitalPartner build() { return new CapitalPartner(this); }
    }
}
