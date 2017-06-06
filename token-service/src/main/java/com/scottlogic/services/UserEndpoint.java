package com.scottlogic.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scottlogic.security.TokenManager;
import com.scottlogic.user.User;
import com.scottlogic.user.UserDao;
import java.io.IOException;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.Response.*;
import static javax.ws.rs.core.Response.Status.*;

@Path("/user")
public class UserEndpoint {
    
    private final static ObjectMapper MAPPER = new ObjectMapper();
    
    private TokenManager tokens;
    private UserDao users;

    public UserEndpoint()
    {
    }
    
    @Inject
    public UserEndpoint(UserDao users, TokenManager tokens)
    {
        this.users = users;
        this.tokens = tokens;
    }
    
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String body) throws IOException
    {
        JsonNode credentials = MAPPER.readTree(body);
        String username = credentials.path("user").asText();
        String password = credentials.path("password").asText();
        
        User u = users.findUser(username);
        
        if(u == null) {
            return status(BAD_REQUEST).entity(
                    "User not found: " + username).build();
        }
        
        if(!u.hasPassword(password)) {
            return status(UNAUTHORIZED).entity(
                    "Incorrect password for " + username).build();
        }
        
        return tokenResponse(username);
    }
    
    @POST
    @Path("create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String body) throws IOException
    {
        JsonNode credentials = MAPPER.readTree(body);
        String username = credentials.path("user").asText();
        String password = credentials.path("password").asText();
        String email = credentials.path("email").asText();
        
        User u = users.findUser(username);
        
        if(u != null) {
            return status(BAD_REQUEST).entity(
                    "User already exists: " + username).build();
        }
        
        users.create(username, password, email);
        
        return tokenResponse(username);
    }
    
    private Response tokenResponse(String username)
    {
        String token = tokens.generateFor(username);
        return ok("{"
            + "\"token\" : \"" + token + "\", "
            + "\"name\" : \"" + username + "\""
        + "}").build();
    }

}
