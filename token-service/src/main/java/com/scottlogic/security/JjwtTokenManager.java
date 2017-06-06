package com.scottlogic.security;

import io.jsonwebtoken.Jwts;
import static io.jsonwebtoken.SignatureAlgorithm.*;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;

public final class JjwtTokenManager implements TokenManager {
    
    private static final Key KEY = MacProvider.generateKey(HS512);
    
    @Override
    public String generateFor(String user)
    {
        return Jwts.builder().setSubject(user).signWith(HS512, KEY).compact();
    }
    
    @Override
    public String extractUserFrom(String token) throws InvalidTokenException
    {
        try {
            return Jwts.parser().setSigningKey(KEY)
                    .parseClaimsJws(token).getBody().getSubject();
        }
        catch(SignatureException ex) {
            throw new InvalidTokenException(ex.getMessage());
        }
    }

}
