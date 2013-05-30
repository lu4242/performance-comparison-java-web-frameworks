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
import springmvcjpa.entity.Hotel;

/**
 *
 * @author lu4242
 */
@Repository("hotelRepositoryBean")
public class HotelRepositoryBean implements HotelRepository
{
    @PersistenceContext(name="bookingDatabase")
    protected EntityManager em;
    
    public List<Hotel> queryHotels(String searchString, int firstResult, int maxResults)
    {
        String pattern = searchString == null ? "%" : '%' + searchString.toLowerCase().replace('*', '%') + '%';
        Query query = em.createQuery("select h from Hotel h"
                + " where lower(h.name) like :pattern"
                + " or lower(h.city) like :pattern"
                + " or lower(h.zip) like :pattern"
                + " or lower(h.address) like :pattern");
        query.setParameter("pattern", pattern);
        query.setMaxResults(maxResults);
        query.setFirstResult(firstResult);
        return query.getResultList();
    }
    
    public Hotel queryFindById(long id)
    {
        return em.find(Hotel.class, id);
    }

}
