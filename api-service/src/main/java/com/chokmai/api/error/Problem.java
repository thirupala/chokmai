package com.chokmai.api.error;

import java.net.URI;

public record Problem(
        URI type,
        String title,
        int status,
        String detail,
        String instance,
        String traceId
) {}
