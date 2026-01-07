package com.chokmai.persistence.entities.stripe;

import com.chokmai.common.enums.CreditLedgerType;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "credit_ledger",
        indexes = {
                @Index(
                        name = "idx_credit_ledger_tenant_id",
                        columnList = "tenant_id, created_at DESC"
                ),
                @Index(
                        name = "idx_credit_ledger_job_id",
                        columnList = "job_id"
                ),
                @Index(
                        name = "idx_credit_ledger_type",
                        columnList = "type, created_at DESC"
                )
        }
)

public class CreditLedgerEntity {

  @Id
  @GeneratedValue
  @Column(nullable = false, updatable = false)
  public UUID id;

  @Column(name = "tenant_id", nullable = false, updatable = false)
  public UUID tenantId;

  @Column(name = "job_id", updatable = false)
  public UUID jobId;

  /**
   * Ledger entry type
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, updatable = false)
  public CreditLedgerType type;

  @Column(nullable = false, updatable = false)
  public int credits;

  @Column(name = "balance_after", nullable = false, updatable = false)
  public int balanceAfter;

  @Column(length = 512, updatable = false)
  public String description;

  @Column(name = "created_at", nullable = false, updatable = false)
  public Instant createdAt = Instant.now();
}

