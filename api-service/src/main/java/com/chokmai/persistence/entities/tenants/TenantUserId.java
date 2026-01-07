package com.chokmai.persistence.entities.tenants;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class TenantUserId implements Serializable {

    @Column(name = "tenant_id")
    private UUID tenantId;

    @Column(name = "user_id")
    private UUID userId;

    // equals & hashCode REQUIRED
}
