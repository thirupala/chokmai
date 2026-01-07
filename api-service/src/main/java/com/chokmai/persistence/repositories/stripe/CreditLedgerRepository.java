package com.chokmai.persistence.repositories.stripe;


import com.chokmai.common.enums.CreditLedgerType;
import com.chokmai.persistence.entities.stripe.CreditLedgerEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.UUID;

@ApplicationScoped
public class CreditLedgerRepository
        implements PanacheRepositoryBase<CreditLedgerEntity, UUID> {

    /**
     * Returns the latest balance for a tenant.
     * If no ledger rows exist, balance is zero.
     */
    public int currentBalance(UUID tenantId) {
        return find(
                "tenantId = ?1 order by createdAt desc",
                tenantId
        )
                .firstResultOptional()
                .map(e -> e.balanceAfter)
                .orElse(0);
    }



    /**
     * Inserts a new immutable ledger entry.
     * This is the ONLY way balances should change.
     */
    @Transactional
    public void insert(
            UUID tenantId,
            UUID jobId,
            CreditLedgerType type,
            int credits,
            int balanceAfter,
            String description
    ) {
        CreditLedgerEntity entry = new CreditLedgerEntity();
        entry.tenantId = tenantId;
        entry.jobId = jobId;
        entry.type = type;
        entry.credits = credits;
        entry.balanceAfter = balanceAfter;
        entry.description = description;

        persist(entry);
    }

    /**
     * Idempotency helper:
     * Check whether a ledger entry of a given type
     * already exists for a job.
     */
    public boolean existsForJob(UUID jobId, String type) {
        return count("jobId = ?1 and type = ?2", jobId, type) > 0;
    }
}

