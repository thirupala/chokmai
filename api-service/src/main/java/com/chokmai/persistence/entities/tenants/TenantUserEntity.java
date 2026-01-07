package com.chokmai.persistence.entities.tenants;

import com.chokmai.common.enums.TenantRole;
import com.chokmai.persistence.entities.users.UserEntity;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "tenant_users")
public class TenantUserEntity {

    @EmbeddedId
    private TenantUserId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TenantRole role;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    // getters/setters
}
