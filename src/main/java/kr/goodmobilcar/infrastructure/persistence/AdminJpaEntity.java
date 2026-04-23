package kr.goodmobilcar.infrastructure.persistence;

import jakarta.persistence.*;
import kr.goodmobilcar.domain.admin.Admin;
import kr.goodmobilcar.domain.admin.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "admins")
public class AdminJpaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String name;

    private String phone;
    private String title;

    @Column(nullable = false)
    private String role;

    @Column(name = "is_must_receive")
    private boolean mustReceive = false;

    @Column(name = "is_live")
    private boolean live = true;

    @Column(name = "order_priority")
    private Integer orderPriority;

    @Column(name = "is_active")
    private boolean active = true;

    @Column(name = "last_login_at")
    private Instant lastLoginAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    protected AdminJpaEntity() {}

    public static AdminJpaEntity fromDomain(Admin a) {
        AdminJpaEntity e = new AdminJpaEntity();
        e.id = a.getId();
        e.email = a.getEmail();
        e.passwordHash = a.getPasswordHash();
        e.name = a.getName();
        e.phone = a.getPhone();
        e.title = a.getTitle();
        e.role = a.getRole().name();
        e.mustReceive = a.isMustReceive();
        e.live = a.isLive();
        e.orderPriority = a.getOrderPriority();
        e.active = a.isActive();
        e.lastLoginAt = a.getLastLoginAt();
        e.createdAt = a.getCreatedAt();
        e.updatedAt = a.getUpdatedAt();
        return e;
    }

    public Admin toDomain() {
        return Admin.builder()
                .id(id).email(email).passwordHash(passwordHash)
                .name(name).phone(phone).title(title)
                .role(Role.valueOf(role))
                .mustReceive(mustReceive).live(live)
                .orderPriority(orderPriority).active(active)
                .lastLoginAt(lastLoginAt)
                .createdAt(createdAt).updatedAt(updatedAt)
                .build();
    }
}
