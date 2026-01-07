package com.chokmai.domain.processors;

import com.chokmai.common.dto.IdResponse;
import com.chokmai.common.dto.processoros.Processor;
import com.chokmai.observability.AuditService;
import com.chokmai.persistence.entities.processors.ProcessorEntity;
import com.chokmai.persistence.repositories.llms.LlmModelRepository;
import com.chokmai.persistence.repositories.processors.ProcessorRepository;
import com.chokmai.security.TenantContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProcessorService {

    @Inject ProcessorRepository processorRepository;
    @Inject TenantContext tenantContext;
    @Inject AuditService auditService;
    @Inject
    LlmModelRepository llmModelRepository;

    @Transactional
    public IdResponse create(CreateProcessorRequest request) {
        UUID effectiveTenantId =
                request.tenantId() != null
                        ? request.tenantId()
                        : tenantContext.tenantIdAsUuid();

        ProcessorEntity processor = new ProcessorEntity();
        processor.id = UUID.randomUUID();
        processor.tenantId = effectiveTenantId;
        processor.name = request.name();
        processor.type = request.type();
        processor.version = request.version();
        processor.jsonSchema = request.configRef();
        processor.createdAt = LocalDateTime.from(Instant.now());

        processorRepository.persist((ProcessorEntity) processor);

        auditService.record(
                tenantContext.actor(),
                UUID.fromString(effectiveTenantId != null ? effectiveTenantId.toString() : "GLOBAL"),
                "PROCESSOR_CREATE",
                processor.id.toString(),
                "SUCCESS"
        );

        return new IdResponse(processor.id);
    }

    public List<Processor> list() {
        return processorRepository.findByTenant(
                        tenantContext.tenantIdAsUuid()
                ).stream()
                .map(this::toDto)
                .toList();
    }
    private Processor toDto(ProcessorEntity e) {
        return new Processor(
                e.id,
                e.name,
                e.type,
                e.tenantId,
                e.createdAt
        );
    }
}
