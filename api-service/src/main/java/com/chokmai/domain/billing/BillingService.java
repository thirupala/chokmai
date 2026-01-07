package com.chokmai.domain.billing;

import com.chokmai.common.dto.CheckoutSessionResponse;
import com.chokmai.common.dto.CreateCheckoutRequest;
import com.chokmai.common.dto.CreditBalance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BillingService {

  @Transactional
  public CreditBalance getCreditBalance(String tenantId) {
    int credits = 0;/* query credit_ledger sum */;
    return new CreditBalance(credits);
  }

  @Transactional
  public CheckoutSessionResponse createCheckoutSession(
      String tenantId,
      CreateCheckoutRequest request) {

    // 1. Create Stripe checkout session
    // 2. Persist stripe_customer if not exists
    // 3. Return hosted checkout URL

    String checkoutUrl = "https://checkout.stripe.com/...";
    return new CheckoutSessionResponse(checkoutUrl);
  }
}
