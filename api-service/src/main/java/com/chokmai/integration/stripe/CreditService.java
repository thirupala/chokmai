package com.chokmai.integration.stripe;


import java.util.UUID;

public interface CreditService {

    void addCredits(UUID customerId, int amount, String reason);

    void chargeCredits(UUID customerId, int amount, String reason);

    int getBalance(UUID customerId);

    void reserve(UUID customerId, UUID jobId,int estimatedCredits);

    void finalizeUsage(UUID customerId, UUID jobId,int reservedCredits,int actualCredits);

    void release(UUID jobId);
}

