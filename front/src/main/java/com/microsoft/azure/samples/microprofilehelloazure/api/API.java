package com.microsoft.azure.samples.microprofilehelloazure.api;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@RequestScoped
@Path("/")
public class API {
  private final static String DISPATCH_SERVICE_URL = "MESSAGE_SERVICE_URL_DISPATCH";

  @GET
  @Path("/{lang}")
  @Produces(TEXT_PLAIN + ";charset=utf-8")
  public String info(@PathParam("lang") String langName) {
      Client client = ClientBuilder.newBuilder()
          .build();
      Config config = ConfigProvider.getConfig();
      String dispatch_service_url = config.getValue(DISPATCH_SERVICE_URL, String.class);
      return client.target(dispatch_service_url + langName)
          .request()
          .header("Content-Type", TEXT_PLAIN)
          .header("Accept", TEXT_PLAIN)
          .get(String.class);
  }
}
