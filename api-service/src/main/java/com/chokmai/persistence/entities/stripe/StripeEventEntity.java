package com.chokmai.persistence.entities.stripe;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "stripe_events", uniqueConstraints = {
    @UniqueConstraint(columnNames = "event_id")
})
public class StripeEventEntity {

  @Id
  public UUID id;

  @Column(name = "event_id", nullable = false)
  public String eventId;

  @Column(nullable = false)
  public String type;

  @Column(columnDefinition = "jsonb")
  public String payload;

  @Column(name = "processed_at")
  public Instant processedAt;
}
