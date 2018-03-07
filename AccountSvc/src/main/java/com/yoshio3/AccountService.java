/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoshio3;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author yoterada
 */
@Path("/rest")
@RequestScoped
public class AccountService {

    @GET
    @Path(value = "/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(
            @HeaderParam("x-request-id") String xreq,
            @HeaderParam("x-b3-traceid") String xtraceid,
            @HeaderParam("x-b3-spanid") String xspanid,
            @HeaderParam("x-b3-parentspanid") String xparentspanid,
            @HeaderParam("x-b3-sampled") String xsampled,
            @HeaderParam("x-b3-flags") String xflags,
            @HeaderParam("x-ot-span-context") String xotspan) {

        // do Somethings...
        
        Response response = invokeAnotherMicroservices(xreq, xtraceid, xspanid, xparentspanid, xsampled, xflags, xotspan);
        return response;
    }

    private Response invokeAnotherMicroservices(
            String xreq,
            String xtraceid,
            String xspanid,
            String xparentspanid,
            String xsampled,
            String xflags,
            String xotspan) {
        Client client = ClientBuilder.newBuilder()
                .build();
        Response response = client.target("http://otherservice.order-system-production.svc.cluster.local/app/other/rest/users")
                .request()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .header("x-request-id", xreq)
                .header("x-b3-traceid", xtraceid)
                .header("x-b3-spanid", xspanid)
                .header("x-b3-parentspanid", xparentspanid)
                .header("x-b3-sampled", xsampled)
                .header("x-b3-flags", xflags)
                .header("x-ot-span-context", xotspan)
                .get();
        return response;
    }
}
