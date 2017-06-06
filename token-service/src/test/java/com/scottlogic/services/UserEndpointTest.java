package com.scottlogic.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scottlogic.security.TokenManager;
import com.scottlogic.user.User;
import com.scottlogic.user.UserDao;
import javax.ws.rs.core.Response;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserEndpointTest {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    @Mock private TokenManager tokens;
    @Mock private UserDao users;
    private UserEndpoint subject;
    private final String request = "{"
        + "\"user\" : \"pete\", "
        + "\"password\" : \"abc123\", "
        + "\"email\" : \"ad@com\""
    + "}";

    @Before
    public void subject() throws Throwable
    {
        subject = new UserEndpoint(users, tokens);
    }
    
    @Before
    public void tokens() throws Throwable
    {
        when(tokens.generateFor("pete")).thenReturn("tok123");
    }
    
    @Test
    public void loginRequest() throws Throwable
    {
        when(users.findUser("pete")).thenReturn(
                new User("pete", "abc123", ""));
        
        Response resp = subject.login(request);

        JsonNode respJson = MAPPER.readTree((String)resp.getEntity());
        String token = respJson.path("token").asText();

        assertThat(resp.getStatus(), is(200));
        assertThat(token, is("tok123"));
        assertThat(respJson.path("name").asText(), is("pete"));
    }
    
    @Test
    public void loginRequestForUnknownUser() throws Throwable
    {
        Response resp = subject.login(request);

        assertThat(resp.getStatus(), is(400));
    }
    
    @Test
    public void loginRequestWithWrongPassword() throws Throwable
    {
        when(users.findUser("pete")).thenReturn(
                new User("pete", "xxx", ""));
        
        Response resp = subject.login(request);

        assertThat(resp.getStatus(), is(401));
    }
    
    @Test
    public void createRequest() throws Throwable
    {
        Response resp = subject.create(request);
        
        verify(users).create("pete", "abc123", "ad@com");

        JsonNode respJson = MAPPER.readTree((String)resp.getEntity());
        String token = respJson.path("token").asText();

        assertThat(resp.getStatus(), is(200));
        assertThat(token, is("tok123"));
        assertThat(respJson.path("name").asText(), is("pete"));
    }
    
    @Test
    public void createRequestForExistingUser() throws Throwable
    {
        when(users.findUser("pete")).thenReturn(new User("", "", ""));
        
        Response resp = subject.create(request);
        
        assertThat(resp.getStatus(), is(400));
    }

}
