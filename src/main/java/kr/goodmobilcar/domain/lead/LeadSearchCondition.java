package kr.goodmobilcar.domain.lead;

import java.time.Instant;

public class LeadSearchCondition {
    private final Instant dateFrom;
    private final Instant dateTo;
    private final Channel channel;
    private final LeadStatus status;
    private final Long assigneeId;
    private final String search;
    private final boolean includeDeleted;
    private final int page;
    private final int size;

    private LeadSearchCondition(Builder builder) {
        this.dateFrom = builder.dateFrom;
        this.dateTo = builder.dateTo;
        this.channel = builder.channel;
        this.status = builder.status;
        this.assigneeId = builder.assigneeId;
        this.search = builder.search;
        this.includeDeleted = builder.includeDeleted;
        this.page = builder.page;
        this.size = builder.size;
    }

    public Instant getDateFrom() { return dateFrom; }
    public Instant getDateTo() { return dateTo; }
    public Channel getChannel() { return channel; }
    public LeadStatus getStatus() { return status; }
    public Long getAssigneeId() { return assigneeId; }
    public String getSearch() { return search; }
    public boolean isIncludeDeleted() { return includeDeleted; }
    public int getPage() { return page; }
    public int getSize() { return size; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Instant dateFrom;
        private Instant dateTo;
        private Channel channel;
        private LeadStatus status;
        private Long assigneeId;
        private String search;
        private boolean includeDeleted = false;
        private int page = 0;
        private int size = 20;

        public Builder dateFrom(Instant v) { this.dateFrom = v; return this; }
        public Builder dateTo(Instant v) { this.dateTo = v; return this; }
        public Builder channel(Channel v) { this.channel = v; return this; }
        public Builder status(LeadStatus v) { this.status = v; return this; }
        public Builder assigneeId(Long v) { this.assigneeId = v; return this; }
        public Builder search(String v) { this.search = v; return this; }
        public Builder includeDeleted(boolean v) { this.includeDeleted = v; return this; }
        public Builder page(int v) { this.page = v; return this; }
        public Builder size(int v) { this.size = v; return this; }
        public LeadSearchCondition build() { return new LeadSearchCondition(this); }
    }
}
