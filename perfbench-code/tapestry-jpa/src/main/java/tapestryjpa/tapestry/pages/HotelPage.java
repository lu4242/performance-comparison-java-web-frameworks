package tapestryjpa.tapestry.pages;

import java.text.DecimalFormat;
import java.util.Calendar;
import javax.persistence.EntityManager;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import tapestryjpa.entity.Booking;
import tapestryjpa.web.BookingSession;
import tapestryjpa.entity.Hotel;
import tapestryjpa.tapestry.services.AppModule;
import tapestryjpa.tapestry.services.JpaEntityManagerFactoryService;

public class HotelPage {    

    @Property
    @SessionState
    private BookingSession session;

    @Property
    private Hotel hotel;
    
    private long hotelId;

    private boolean backSubmitted;
    
    //@Inject
    //private JpaService jpa;
    
    @Inject
    private JpaEntityManagerFactoryService jpaEmf;

    @InjectPage
    private BookPage bookPage;

    public DecimalFormat getCurrencyFormat() {
        return AppModule.CURRENCY_FORMAT;
    }

    void onSelectedFromBack() {
        backSubmitted = true;
    }

    Object onSuccess() {
        if(backSubmitted) {
            return MainPage.class;
        }
        Booking booking = new Booking(hotel, session.getUser());
        Calendar calendar = Calendar.getInstance();
        booking.setCheckinDate(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        booking.setCheckoutDate(calendar.getTime());
        int flowId = session.startFlow(booking);
        bookPage.setFlowId(flowId);
        return bookPage;
    }

    void onActivate(long hotelId) {
        this.hotelId = hotelId;
        EntityManager em = jpaEmf.createEntityManager();
        em.getTransaction().begin();
        try
        {        
            hotel = em.find(Hotel.class, hotelId);
        }
        finally
        {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            em.close();
        }
    }

    long onPassivate() {
        return hotelId;
    }

}
