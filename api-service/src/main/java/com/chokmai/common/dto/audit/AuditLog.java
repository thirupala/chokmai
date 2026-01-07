package com.chokmai.common.dto.audit;

import java.time.Instant;

public record AuditLog(
    String actor,
    String tenantId,
    String action,
    String resource,
    String outcome,
    Instant timestamp
) {}
