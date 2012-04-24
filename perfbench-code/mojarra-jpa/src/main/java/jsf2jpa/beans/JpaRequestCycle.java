/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf2jpa.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author lu4242
 */
@ManagedBean(name="jpaRequestCycle")
@RequestScoped
public class JpaRequestCycle
{
    @ManagedProperty(value="#{bookingApplication}")
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
