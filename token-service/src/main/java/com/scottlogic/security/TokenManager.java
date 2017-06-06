package com.scottlogic.security;

public interface TokenManager {
    
    String generateFor(String user);
    String extractUserFrom(String token) throws InvalidTokenException;
    
    public static final class InvalidTokenException extends Exception {

        public InvalidTokenException(String msg)
        {
            super(msg);
        }
        
    }

}
