package kr.goodmobilcar.domain.admin;

import java.time.Instant;

public class Admin {

    private final Long id;
    private final String email;
    private final String passwordHash;
    private final String name;
    private final String phone;
    private final String title;
    private final Role role;
    private final boolean mustReceive;
    private final boolean live;
    private final Integer orderPriority;
    private final boolean active;
    private final Instant lastLoginAt;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Admin(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.passwordHash = builder.passwordHash;
        this.name = builder.name;
        this.phone = builder.phone;
        this.title = builder.title;
        this.role = builder.role;
        this.mustReceive = builder.mustReceive;
        this.live = builder.live;
        this.orderPriority = builder.orderPriority;
        this.active = builder.active;
        this.lastLoginAt = builder.lastLoginAt;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public boolean canReceiveNotification() {
        return active && (live || mustReceive);
    }

    public Admin withLive(boolean live) {
        return toBuilder().live(live).build();
    }

    public Admin withOrderPriority(Integer priority) {
        return toBuilder().orderPriority(priority).build();
    }

    public Admin recordLogin() {
        return toBuilder().lastLoginAt(Instant.now()).build();
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getTitle() { return title; }
    public Role getRole() { return role; }
    public boolean isMustReceive() { return mustReceive; }
    public boolean isLive() { return live; }
    public Integer getOrderPriority() { return orderPriority; }
    public boolean isActive() { return active; }
    public Instant getLastLoginAt() { return lastLoginAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public static Builder builder() { return new Builder(); }

    public Builder toBuilder() {
        return new Builder()
                .id(id).email(email).passwordHash(passwordHash).name(name)
                .phone(phone).title(title).role(role).mustReceive(mustReceive)
                .live(live).orderPriority(orderPriority).active(active)
                .lastLoginAt(lastLoginAt).createdAt(createdAt).updatedAt(updatedAt);
    }

    public static class Builder {
        private Long id;
        private String email;
        private String passwordHash;
        private String name;
        private String phone;
        private String title;
        private Role role = Role.ADMIN;
        private boolean mustReceive = false;
        private boolean live = true;
        private Integer orderPriority;
        private boolean active = true;
        private Instant lastLoginAt;
        private Instant createdAt;
        private Instant updatedAt;

        public Builder id(Long v) { this.id = v; return this; }
        public Builder email(String v) { this.email = v; return this; }
        public Builder passwordHash(String v) { this.passwordHash = v; return this; }
        public Builder name(String v) { this.name = v; return this; }
        public Builder phone(String v) { this.phone = v; return this; }
        public Builder title(String v) { this.title = v; return this; }
        public Builder role(Role v) { this.role = v; return this; }
        public Builder mustReceive(boolean v) { this.mustReceive = v; return this; }
        public Builder live(boolean v) { this.live = v; return this; }
        public Builder orderPriority(Integer v) { this.orderPriority = v; return this; }
        public Builder active(boolean v) { this.active = v; return this; }
        public Builder lastLoginAt(Instant v) { this.lastLoginAt = v; return this; }
        public Builder createdAt(Instant v) { this.createdAt = v; return this; }
        public Builder updatedAt(Instant v) { this.updatedAt = v; return this; }
        public Admin build() { return new Admin(this); }
    }
}
