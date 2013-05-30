/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package springmvcjpa.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import springmvcjpa.entity.User;

/**
 *
 * @author lu4242
 */
@Repository("userRepositoryBean")
public class UserRepositoryBean implements UserRepository
{
    @PersistenceContext(name="bookingDatabase")
    protected EntityManager em;
    
    public User authenticate(String username, String password)
    {
        Query query = em.createQuery("select u from User u"
                + " where u.username = :username and u.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);

        List<User> users = query.getResultList();
        if (users.isEmpty())
        {
            return null;
        }
        return users.get(0);
    }
}
