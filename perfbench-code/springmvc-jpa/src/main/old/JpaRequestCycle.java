/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package springmvcjpa.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author lu4242
 */
@Component("jpaRequestCycle")
@Scope("request")
public class JpaRequestCycle
{
    @Autowired
    private BookingApplication bookingApplication;
    
    private EntityManager em;
     
    EntityManager getEntityManager()
    {
        return em;
    }
    
    @PostConstruct
    public void init()
    {
        em = getBookingApplication().getEntityManagerFactory().createEntityManager();
        //em.getTransaction().begin();
    }
    
    @PreDestroy
    public void destroy()
    {
        if (em != null)
        {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            em.close();
        }
    }

        /**
     * @return the bookingApplication
     */
    public BookingApplication getBookingApplication() {
        return bookingApplication;
    }

    /**
     * @param bookingApplication the bookingApplication to set
     */
    public void setBookingApplication(BookingApplication bookingApplication) {
        this.bookingApplication = bookingApplication;
    }
}
