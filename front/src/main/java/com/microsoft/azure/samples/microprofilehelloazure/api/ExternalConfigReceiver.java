package com.microsoft.azure.samples.microprofilehelloazure.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/*
  This is the sample of Microprofile Config API.

  The priority to find the configuration is as follows.

  1. System property (ordinal: 400)
  2. Environment Variable (ordinal: 300)
  3. microprofile-config.properties (ordinal: 100)
*/


@Path("/env")
@RequestScoped
public class ExternalConfigReceiver {

    @Inject
    @ConfigProperty(name = "JAVA_HOME", defaultValue = "/Library/Java/JavaVirtualMachines")
    private String javaHomeEnvValue;

    @GET
    @Path("/injected")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInjectedEnvironmentValues() {
        JsonObject json = Json.createObjectBuilder()
                .add("injected", javaHomeEnvValue)
                .build();
        String jsonResult = json.toString();
        return Response.ok(jsonResult, MediaType.APPLICATION_JSON_TYPE).build();

    }

    @GET
    @Path("{key}")
    public Response getConfig(@PathParam("key") String keyName) {

        Config config = ConfigProvider.getConfig();
        String value = config.getValue(keyName, String.class);

        JsonObject json = Json.createObjectBuilder()
                .add(keyName, value)
                .build();
        String jsonResult = json.toString();

        return Response.ok(jsonResult, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/lists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllEnvironmentValues() {
        try {
            Map<String, String> env = System.getenv();
            String json = new ObjectMapper().writeValueAsString(env);
            return Response.ok(json, MediaType.APPLICATION_JSON_TYPE).build();
        } catch (JsonProcessingException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
