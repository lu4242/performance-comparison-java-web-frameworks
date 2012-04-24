package tapestryjpa.tapestry.pages;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import tapestryjpa.entity.Booking;
import tapestryjpa.web.BookingSession;
import tapestryjpa.entity.Hotel;
import tapestryjpa.tapestry.services.JpaService;

public class MainPage {
    
    @Property
    @Persist(PersistenceConstants.FLASH)
    private String message;

    @Property
    @SessionState
    private BookingSession session;

    @Inject
    private JpaService jpa;

    @Component
    private Form form;

    @Property
    private Hotel hotel;

    @Property
    private Booking booking;

    @InjectComponent
    private Zone hotelsZone;

    @Inject
    private Logger logger;

    public boolean isNextPageAvailable() {
        return session.getHotels() != null && session.getHotels().size() == session.getPageSize();
    }

    void onActivate() {
        if(session.getBookings() == null) {
            session.loadBookings(jpa.getEntityManager());
        }
    }

    Object onSuccess() {
        session.setPage(0);
        loadHotels();
        return hotelsZone.getBody();
    }

    void onActionFromNextPage() {
        session.setPage(session.getPage() + 1);
        loadHotels();
    }

    void loadHotels() {
        String searchString = session.getSearchString();
        String pattern = searchString == null ? "%" : '%' + searchString.toLowerCase().replace('*', '%') + '%';
        Query query = jpa.getEntityManager().createQuery("select h from Hotel h"
                + " where lower(h.name) like :pattern"
                + " or lower(h.city) like :pattern"
                + " or lower(h.zip) like :pattern"
                + " or lower(h.address) like :pattern");
        query.setParameter("pattern", pattern);
        query.setMaxResults(session.getPageSize());
        query.setFirstResult(session.getPage() * session.getPageSize());
        session.setHotels(query.getResultList());
    }

    void onActionFromCancelBooking(long bookingId) {
        EntityManager em = jpa.getEntityManager();
        Booking cancelled = em.find(Booking.class, bookingId);        
        if (cancelled != null) {
            logger.info("Cancel booking: {} for {}", cancelled.getId(), session.getUser().getUsername());
            em.remove(cancelled);            
            message = "Booking cancelled for confirmation number " + cancelled.getId();
        }
        session.loadBookings(em);
    }
    
}
