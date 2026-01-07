package com.chokmai.integration.stripe;

import com.chokmai.common.enums.CreditLedgerType;
import com.chokmai.persistence.repositories.stripe.CreditLedgerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.UUID;

@ApplicationScoped
public class CreditLedgerService {

    @Inject
    CreditLedgerRepository ledger;

    @Transactional
    public void recordUsage(
            UUID tenantId,
            UUID jobId,
            int credits,
            String description
    ) {
        int balanceBefore = ledger.currentBalance(tenantId);

        int balanceAfter = balanceBefore - credits;

        if (balanceAfter < 0) {
            throw new IllegalStateException("Insufficient credits");
        }

        ledger.insert(
                tenantId,
                jobId,
                CreditLedgerType.usage,
                credits,
                balanceAfter,
                description
        );
    }

    @Transactional
    public void recordRefund(
            UUID tenantId,
            UUID jobId,
            int credits,
            String description
    ) {
        int balanceBefore=ledger.currentBalance(tenantId);
        int balanceAfter = balanceBefore + credits;

        ledger.insert(
                tenantId,
                jobId,
                CreditLedgerType.refund,
                credits,
                balanceAfter,
                description
        );
    }
}

