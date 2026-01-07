package com.chokmai.observability;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Interceptor
@Audit
public class AuditInterceptor {

    @Inject
    AuditService audit;
    @AroundInvoke
    public Object log(InvocationContext ctx) throws Exception {
        audit.record(
                ctx.getMethod().getName(),
                ctx.getTarget().getClass().getSimpleName()
        );
        return ctx.proceed();
    }
}
