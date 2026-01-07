package com.chokmai.persistence.repositories.audit;

import com.chokmai.persistence.entities.audit.AuditLogEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class AuditLogRepository
        implements PanacheRepositoryBase<AuditLogEntity,UUID> {

    public List<AuditLogEntity> listForTenant(
            UUID tenantId,
            int limit,
            int offset) {

        return find("tenantId", tenantId)
                .page(offset / limit, limit)
                .list();
    }

    public List<AuditLogEntity> listAll(int limit, int offset) {
        return findAll()
                .page(offset / limit, limit)
                .list();
    }
}
