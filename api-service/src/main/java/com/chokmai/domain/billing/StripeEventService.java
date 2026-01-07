package com.chokmai.domain.billing;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StripeEventService {

  @Transactional
  public void processEvent(String payload, String signature) {
    // 1. Verify Stripe signature
    // 2. Parse event
    // 3. Check stripe_events table (idempotency)
    // 4. On checkout.session.completed:
    //    - credit tenant
    //    - insert CREDIT_PURCHASE ledger entry
  }
}
