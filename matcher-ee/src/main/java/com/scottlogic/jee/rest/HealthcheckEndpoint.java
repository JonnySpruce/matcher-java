package com.scottlogic.jee.rest;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.Response.*;

@Path("/healthcheck")
public class HealthcheckEndpoint {

    @GET
    @Produces("text/plain")
    public Response doGet()
    {
        return ok().build();
    }

}
