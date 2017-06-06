package com.scottlogic.user;

public interface UserDao {
    
    User findUser(String username);
    User create(String username, String password, String email);

}
