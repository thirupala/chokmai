package com.chokmai.observability;

import com.chokmai.persistence.entities.audit.AuditLogEntity;
import com.chokmai.persistence.repositories.audit.AuditLogRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class AuditService {

    @Inject
    AuditLogRepository auditRepository;

    @Transactional
    public void record(
            String actor,
            UUID tenantId,
            String action,
            String resource,
            String outcome) {

        AuditLogEntity entity = new AuditLogEntity();
        entity.id = UUID.randomUUID();
        entity.actor = actor;
        entity.tenantId = tenantId;
        entity.action = action;
        entity.resource = resource;
        entity.outcome = outcome;
        entity.createdAt = Instant.now();

        auditRepository.persist(entity);
    }

    /**
     * Convenience overload when no tenant is available.
     */
    @Transactional
    public void record(
            String actor,
            String action,
            String resource,
            String outcome) {

        record(actor, null, action, resource, outcome);
    }

    public void record(String methodName, String classSimpleName) {
        record(
                "system",                // actor (interceptor-level)
                null,                    // tenantId (may not be available here)
                methodName,              // action
                classSimpleName,         // resource
                "INVOKED"                // outcome
        );
    }
}
