package com.scottlogic.jee.rest;

import com.scottlogic.common.util.IntegrationTest;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import static org.hamcrest.CoreMatchers.*;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

@RunWith(Arquillian.class)
@DefaultDeployment
@Category(IntegrationTest.class)
public class ServerHealthTest {
    
    private final OkHttpClient client = new OkHttpClient();

    @Test
    @RunAsClient
    public void serverUp() throws Throwable
    {
        Request request = new Request.Builder()
                .url("http://localhost:8080/healthcheck")
                .build();
        
        Response response = client.newCall(request).execute();
        
        assertThat(response.code(), is(200));
    }

}
