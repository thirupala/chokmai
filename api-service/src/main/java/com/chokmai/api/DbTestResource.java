package com.chokmai.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Path("/test")
public class DbTestResource {

    @Inject
    DataSource dataSource;

    @GET
    @Path("/db")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> testDb() {
        Map<String, String> result = new HashMap<>();
        try (var conn = dataSource.getConnection()) {
            result.put("status", "connected");
            result.put("url", conn.getMetaData().getURL());
            result.put("database", conn.getCatalog());
            result.put("user", conn.getMetaData().getUserName());
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", e.getMessage());
        }
        return result;
    }
}
