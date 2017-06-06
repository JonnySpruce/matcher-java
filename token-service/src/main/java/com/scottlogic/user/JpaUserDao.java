package com.scottlogic.user;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class JpaUserDao implements UserDao {
    
    @PersistenceContext(unitName="users") private EntityManager em;

    @Override
    public User findUser(String username)
    {
        try {
            return (User)em.createNamedQuery("User.findByName")
                    .setParameter("name", username).getSingleResult();
        }
        catch(NoResultException ex) {
            return null;
        }
    }

    @Override
    public User create(String username, String password, String email)
    {
        User u = new User(username, password, email);
        em.persist(u);
        return u;
    }
    
}
