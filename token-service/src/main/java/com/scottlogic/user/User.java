package com.scottlogic.user;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="USERS")
@NamedQuery(
    name = "User.findByName",
    query = "SELECT u FROM User u WHERE u.username = :name"
)
public class User implements Serializable {
    
    @Id private String username;
    private String password;
    private String email;

    public User()
    {
    }
    
    public User(String username, String password, String email)
    {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public boolean hasPassword(String password)
    {
        return this.password.equals(password);
    }
    
}
