package com.scottlogic.security;

import javax.enterprise.inject.Alternative;
import static org.apache.commons.lang3.StringUtils.*;

@Alternative
public final class SimpleTokenManager implements TokenManager {
    
    @Override
    public String generateFor(String user)
    {
        return reverse(user);
    }
    
    @Override
    public String extractUserFrom(String token)
    {
        return reverse(token);
    }

}
