package com.chokmai.integration.stripe;

import com.chokmai.common.enums.CreditLedgerType;
import com.chokmai.persistence.entities.jobs.JobEntity;
import com.chokmai.persistence.repositories.jobs.JobRepository;
import com.chokmai.persistence.repositories.stripe.CreditLedgerRepository;
import com.stripe.model.Customer;
import com.stripe.param.CustomerBalanceTransactionCreateParams;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class StripeCreditService implements CreditService {


    private static final long CENTS = 100L;

    @Inject
    JobRepository jobRepository;

    @Inject
    CreditLedgerRepository creditLedgerRepo;

    @Override
    public void addCredits(UUID customerId, int amount, String reason) {
        // Negative amount = credit
        createBalanceTransaction(customerId, amount, reason);
    }

    //customerId is tenant id
    @Override
    public void chargeCredits(UUID customerId, int amount, String reason) {
        // Positive amount = debit
        createBalanceTransaction(customerId, amount, reason);
    }

    //customerId is tenant id
    @Override
    public int getBalance(UUID customerId) {
        try {
            Customer customer = Customer.retrieve(String.valueOf(customerId));
            return Math.toIntExact((customer.getBalance() / CENTS));
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch customer balance", e);
        }
    }


    private void createBalanceTransaction(UUID customerId, int amount, String description) {
        try {
            long stripeAmount = amount * CENTS;

            CustomerBalanceTransactionCreateParams params = CustomerBalanceTransactionCreateParams.builder()
                    .setAmount(stripeAmount)
                    .setCurrency("usd")
                    .setDescription(description)
                    .build();

            Customer customer = Customer.retrieve(String.valueOf(customerId));
            customer.balanceTransactions().create((Map<String, Object>) params);

        } catch (Exception e) {
            throw new RuntimeException("Stripe balance transaction failed", e);
        }
    }

    @Transactional
    public void reserve(UUID customerId, UUID jobId, int estimatedCredits) {
        JobEntity job = jobRepository.findById(jobId);
        if (job == null) {
            throw new IllegalStateException("Job not found: " + jobId);
        }
        if (job.estimatedCredits > 0) {
            // idempotent: already reserved
            return;
        }
        // Safety: ensure sufficient balance
        int balance = getBalance(customerId);
        if (balance-estimatedCredits < 0) {
            throw new IllegalStateException("Insufficient credits");
        }
        // Debit immediately
        chargeCredits(customerId, estimatedCredits, "Reserve credits for job " + jobId);
        // Persist reservation
        job.estimatedCredits = estimatedCredits;
        job.updatedAt = Instant.now();
    }

    @Transactional
    public void finalizeUsage(UUID customerId, UUID jobId, int reservedCredits, int actualCredits) {

        JobEntity job = jobRepository.findById(jobId);
        int reserved = job.estimatedCredits;

        int currentBalance = creditLedgerRepo.currentBalance(customerId);

        // 1️Usage
        int balanceAfterUsage = currentBalance - actualCredits;
        creditLedgerRepo.insert(
                customerId,
                jobId,
                CreditLedgerType.usage,
                actualCredits,
                balanceAfterUsage,
                "Job execution"
        );

        // 2️Refund unused portion
        int refund = reserved-actualCredits;
        if (refund> 0) {
            int balanceAfterRefund = balanceAfterUsage + refund;
            creditLedgerRepo.insert(
                    customerId,
                    jobId,
                    CreditLedgerType.refund,
                    refund,
                    balanceAfterRefund,
                    "Unused credits refund"
            );
        }

        // 3️Update job
        job.actualCredits = actualCredits;
        job.estimatedCredits = 0;
        job.completedAt = Instant.now();
        job.status = "completed";
    }


    @Transactional
    public void release(UUID jobId) {
        JobEntity job = jobRepository.findById(jobId);
        // No ledger entry
        job.estimatedCredits = 0;
        job.status = "failed";
        job.updatedAt = Instant.now();
    }

    @Transactional
    public void purchaseCredits(
            UUID tenantId,
            UUID stripeCustomerId,
            int credits,
            String description
    ) {
        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be positive");
        }

        try {
            long stripeAmountCents = credits * CENTS;
            //  Works on all Stripe SDK versions
            createBalanceTransaction(stripeCustomerId, (int) -stripeAmountCents, description);

            int currentBalance = creditLedgerRepo.currentBalance(tenantId);
            int balanceAfter = currentBalance + credits;

            creditLedgerRepo.insert(
                    tenantId,
                    null,
                    CreditLedgerType.purchase,
                    credits,
                    balanceAfter,
                    description
            );

        } catch (Exception e) {
            throw new RuntimeException("Stripe purchase failed", e);
        }
    }

}

