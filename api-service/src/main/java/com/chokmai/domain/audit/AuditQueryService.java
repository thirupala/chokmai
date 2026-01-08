package com.chokmai.domain.audit;

import com.chokmai.common.dto.audit.AuditLog;
import com.chokmai.security.AuthContext;
import com.chokmai.security.TenantContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AuditQueryService {

  @Inject
  AuthContext authContext;

  @Inject
  TenantContext tenantContext;

  public List<AuditLog> list(int limit, int offset) {
    UUID tenantId = UUID.fromString(tenantContext.tenantId());

    // If sysadmin → return all
    // Else → filter by tenantId

    return List.of(); // map from audit_logs table
  }
}
