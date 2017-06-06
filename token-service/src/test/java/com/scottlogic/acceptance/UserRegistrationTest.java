package com.scottlogic.acceptance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.google.common.collect.Maps.*;
import com.squareup.okhttp.Response;
import java.util.Map;
import static org.hamcrest.CoreMatchers.*;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.*;
import static org.junit.Assert.*;

public class UserRegistrationTest extends DefaultAcceptanceTest {
    
    private final static ObjectMapper MAPPER = new ObjectMapper();
    
    @Test
    @RunAsClient
    public void userCanLogIn() throws Throwable
    {
        Map<String, String> reqBody = newHashMap();
        reqBody.put("user", "U0");
        reqBody.put("password", "000");
        
        Response response = httpPostAsJson("user/login", reqBody);
        
        assertThat(response.code(), is(200));
        
        JsonNode actual = MAPPER.readTree(response.body().string());
        assertThat(actual.get("token").asText(), is("0U"));
        assertThat(actual.get("name").asText(), is("U0"));
    }
    
    @Test
    @RunAsClient
    public void unknownUserDenied() throws Throwable
    {
        Map<String, String> reqBody = newHashMap();
        reqBody.put("user", "New User");
        reqBody.put("password", "000");
        
        Response response = httpPostAsJson("user/login", reqBody);
        
        assertThat(response.code(), is(400));
    }
    
    @Test
    @RunAsClient
    public void wrongPasswordDenied() throws Throwable
    {
        Map<String, String> reqBody = newHashMap();
        reqBody.put("user", "U0");
        reqBody.put("password", "wrong");
        
        Response response = httpPostAsJson("user/login", reqBody);
        
        assertThat(response.code(), is(401));
    }
    
    @Test
    @RunAsClient
    public void userCanRegisterNewAccount() throws Throwable
    {
        Map<String, String> reqBody = newHashMap();
        reqBody.put("user", "u123");
        reqBody.put("password", "p456");
        reqBody.put("email", "a@b.com");
        
        Response response = httpPostAsJson("user/create", reqBody);
        
        assertThat(response.code(), is(200));
        
        JsonNode actual = MAPPER.readTree(response.body().string());
        assertThat(actual.get("token").asText(), is("321u"));
        assertThat(actual.get("name").asText(), is("u123"));
    }
    
    @Test
    @RunAsClient
    public void userCannotReRegisterExistingAccount() throws Throwable
    {
        Map<String, String> reqBody = newHashMap();
        reqBody.put("user", "U0");
        reqBody.put("password", "000");
        reqBody.put("email", "a@b.com");
        
        Response response = httpPostAsJson("user/create", reqBody);
        
        assertThat(response.code(), is(400));
    }

}
