package com.chokmai.api.error;

import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.net.URI;

@Provider
public class ForbiddenMapper implements ExceptionMapper<ForbiddenException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ForbiddenException ex) {
        return Response.status(403)
                .type("application/problem+json")
                .entity(new Problem(
                        URI.create("https://api.chokma.com/problems/forbidden"),
                        "Forbidden",
                        403,
                        ex.getMessage(),
                        uriInfo.getPath(),
                        null
                ))
                .build();
    }
}