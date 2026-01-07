package com.chokmai.common.dto.jobs;

import java.time.Instant;
import java.util.UUID;

public record JobResponse(
        UUID jobId,
        String status,
        UUID projectId,
        UUID processorId,
        Instant createdAt,
        Instant completedAt
) {


}
