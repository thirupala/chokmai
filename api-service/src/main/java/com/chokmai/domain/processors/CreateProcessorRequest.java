package com.chokmai.domain.processors;

import java.util.UUID;

public record CreateProcessorRequest(
        String name,
        String type,
        String version,
        String configRef,
        UUID tenantId   // null → global processor (sysadmin only)
) {}