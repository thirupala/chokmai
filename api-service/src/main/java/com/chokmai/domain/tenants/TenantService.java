package com.chokmai.domain.tenants;

import com.chokmai.common.dto.IdResponse;
import com.chokmai.common.dto.tenants.Tenant;
import com.chokmai.observability.AuditService;
import com.chokmai.persistence.entities.tenants.TenantEntity;
import com.chokmai.persistence.repositories.tenants.TenantRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TenantService {

    @Inject TenantRepository tenantRepository;
    @Inject AuditService auditService;

    @Transactional
    public IdResponse create(CreateTenantRequest request) {

        if (tenantRepository.existsByKey(request.key())) {
            throw new IllegalArgumentException("Tenant key already exists");
        }

        TenantEntity tenant = new TenantEntity();
        tenant.id = UUID.randomUUID();
        tenant.key = request.key();
        tenant.name = request.name();
        tenant.status = "ACTIVE";
        tenant.createdAt = Instant.now();

        tenantRepository.persist(tenant);

        auditService.record(
                "SYSTEM",
                tenant.id,
                "TENANT_CREATE",
                tenant.key,
                "SUCCESS"
        );

        return new IdResponse(tenant.id);
    }

    public List<Tenant> listTenants() {
        return tenantRepository.findAll()
                .stream()
                .map(Tenant::fromEntity)
                .toList();
    }

    public List<Tenant> list(int page, int size) {
        return tenantRepository.findAll()
                .page(page, size)
                .stream()
                .map(Tenant::fromEntity)
                .toList();
    }


    public void delete(String tenantId) {
        tenantRepository.delete(tenantId);
    }
}
