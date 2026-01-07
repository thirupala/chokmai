package com.chokmai.persistence.entities.stripe;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "stripe_customers")
public class StripeCustomerEntity {

  @Id
  @Column(name = "tenant_id")
  public UUID tenantId;

  @Column(name = "stripe_customer_id", nullable = false)
  public String stripeCustomerId;
}
