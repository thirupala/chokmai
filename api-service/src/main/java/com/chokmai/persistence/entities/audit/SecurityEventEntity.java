package com.chokmai.persistence.entities.audit;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "security_events")
public class SecurityEventEntity {

  @Id
  public UUID id;

  @Column(name = "event_type", nullable = false)
  public String eventType;

  @Column(columnDefinition = "jsonb")
  public String details;

  @Column(name = "created_at", nullable = false)
  public Instant createdAt = Instant.now();
}
