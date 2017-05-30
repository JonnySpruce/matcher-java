package com.scottlogic.jee.acceptance;

import com.squareup.okhttp.Response;
import static org.hamcrest.CoreMatchers.*;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.*;
import static org.junit.Assert.*;

public class ServerHealthTest extends DefaultAcceptanceTest {
    
    @Test
    @RunAsClient
    public void serverUp() throws Throwable
    {
        Response response = httpGet("healthcheck");
        
        assertThat(response.code(), is(200));
    }

}
