package com.scottlogic.jee.rest;

import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.*;
import org.junit.*;
import static org.junit.Assert.*;

public class HealthcheckEndpointTest {
    
    private final HealthcheckEndpoint endpoint = new HealthcheckEndpoint();

    @Test
    public void doGetReturnsOkResponse()
    {
        Response actual = endpoint.doGet();
        
        assertThat("response", actual, notNullValue());
        assertThat("response status", actual.getStatus(), is(200));
    }

}