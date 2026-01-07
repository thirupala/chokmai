package com.chokmai.common.dto.processoros;

import java.time.LocalDateTime;
import java.util.UUID;

public class Processor {

    public UUID id;
    public String name;
    public String type;
    public String status;
    public UUID tenantId;
    public LocalDateTime createdAt;

    public Processor() {
        // default constructor for serialization
    }

    public Processor(
            UUID id,
            String name,
            String type,
            UUID tenantId,
            LocalDateTime createdAt) {

        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.tenantId = tenantId;
        this.createdAt = createdAt;
    }
}
