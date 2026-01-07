package com.chokmai.domain.llm;

import com.chokmai.common.dto.llm.SchemaResponse;
import com.chokmai.observability.AuditService;
import com.chokmai.persistence.entities.llm.LlmSchemaEntity;
import com.chokmai.persistence.repositories.llms.LlmSchemaRepository;
import com.chokmai.security.TenantContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class LlmSchemaService {

    @Inject
    LlmSchemaRepository schemaRepository;

    @Inject
    TenantContext tenantContext;

    @Inject
    AuditService auditService;

    /**
     * Create a new LLM extraction schema for a tenant
     */
    @Transactional
    public SchemaResponse create(CreateSchemaRequest request) {
        UUID tenantId = tenantContext.tenantIdAsUuid();

        LlmSchemaEntity entity = new LlmSchemaEntity();
        entity.id = UUID.randomUUID();
        entity.tenantId = tenantId;
        entity.name = request.name();
        entity.description = request.description();
        entity.modelProvider = request.modelProvider();
        entity.modelName = request.modelName();
        entity.schemaJson = request.schemaDefinition();
        entity.createdAt = Instant.now();

        schemaRepository.persist(entity);

        auditService.record(
                tenantContext.actor(),
                tenantId,
                "LLM_SCHEMA_CREATE",
                entity.id.toString(),
                "SUCCESS"
        );

        return new SchemaResponse(
                entity.id,
                entity.name,
                entity.description,
                entity.modelProvider,
                entity.modelName,
                entity.createdAt
        );
    }
}
