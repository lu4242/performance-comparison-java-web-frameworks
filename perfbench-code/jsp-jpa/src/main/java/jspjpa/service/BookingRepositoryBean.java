/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import jspjpa.entity.Booking;

/**
 *
 * @author lu4242
 */
//@Repository("bookingRepositoryBean")
public class BookingRepositoryBean implements BookingRepository
{
    //@PersistenceContext(name="bookingDatabase")
    protected EntityManager em;
    
    public BookingRepositoryBean(EntityManager em)
    {
        this.em = em;
    }

    public List<Booking> queryBookings(String username)
    {
        Query query = em.createQuery("select b from Booking b"
                + " where b.user.username = :username order by b.checkinDate");
        query.setParameter("username", username);
        return query.getResultList();
    }
    
    public Booking find(Long id)
    {
        return em.find(Booking.class, id);
    }

    //@Transactional(propagation = Propagation.REQUIRED)
    public void cancel(Long id)
    {
        Booking cancelled = em.find(Booking.class, id);
        if (cancelled != null)
        {
            try
            {
                em.getTransaction().begin();
                em.remove(cancelled);
                em.getTransaction().commit();
            }
            finally
            {
                if (em.getTransaction().isActive())
                {
                    em.getTransaction().rollback();
                }
            }
        }
    }
    
    //@Transactional(propagation = Propagation.REQUIRED)
    public void create(Booking b)
    {
        try
        {
            em.getTransaction().begin();
            em.persist(b);
            em.getTransaction().commit();
        }
        finally
        {
            if (em.getTransaction().isActive())
            {
                em.getTransaction().rollback();
            }
        }
    }
}
