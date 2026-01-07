package com.chokmai.domain.audit;

import com.chokmai.common.dto.audit.AuditLog;
import com.chokmai.security.TenantContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class AuditQueryService {

  @Inject
  TenantContext tenantContext;

  public List<AuditLog> list(int limit, int offset) {
    String tenantId = tenantContext.tenantId();

    // If sysadmin → return all
    // Else → filter by tenantId

    return List.of(); // map from audit_logs table
  }
}
