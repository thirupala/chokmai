package com.chokmai.persistence.entities.audit;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
public class AuditLogEntity {

  @Id
  public UUID id;

  @Column(nullable = false)
  public String actor;

  @Column(name = "tenant_id")
  public UUID tenantId;

  @Column(nullable = false)
  public String action;

  @Column(nullable = false)
  public String resource;

  @Column(nullable = false)
  public String outcome;

  @Column(name = "created_at", nullable = false)
  public Instant createdAt = Instant.now();
}
