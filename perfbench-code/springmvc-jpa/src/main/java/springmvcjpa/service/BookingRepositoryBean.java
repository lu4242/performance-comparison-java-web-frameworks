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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import springmvcjpa.entity.Booking;

/**
 *
 * @author lu4242
 */
@Repository("bookingRepositoryBean")
public class BookingRepositoryBean implements BookingRepository
{
    @PersistenceContext(name="bookingDatabase")
    protected EntityManager em;

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

    @Transactional(propagation = Propagation.REQUIRED)
    public void cancel(Long id)
    {
        Booking cancelled = em.find(Booking.class, id);
        if (cancelled != null) {
            em.remove(cancelled);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    public void create(Booking b)
    {
        em.persist(b);
    }
}
