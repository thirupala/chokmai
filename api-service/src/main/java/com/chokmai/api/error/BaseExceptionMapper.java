package com.chokmai.api.error;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.net.URI;
import java.util.UUID;

@Provider
public class BaseExceptionMapper implements ExceptionMapper<Throwable> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable ex) {
        int status = 500;
        String title = "Internal Server Error";

        if (ex instanceof WebApplicationException wae) {
            status = wae.getResponse().getStatus();
            title = Response.Status.fromStatusCode(status).getReasonPhrase();
        }

        Problem problem = new Problem(
                URI.create("https://api.chokma.com/problems/" + status),
                title,
                status,
                ex.getMessage(),
                uriInfo.getPath(),
                UUID.randomUUID().toString()
        );

        return Response.status(status)
                .type("application/problem+json")
                .entity(problem)
                .build();
    }
}