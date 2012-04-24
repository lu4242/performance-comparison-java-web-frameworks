package tapestryjpa.tapestry.pages;

import java.text.DecimalFormat;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import tapestryjpa.entity.Booking;
import tapestryjpa.tapestry.services.AppModule;
import tapestryjpa.tapestry.services.JpaService;
import tapestryjpa.web.BookingSession;

public class ConfirmPage {

    @Persist(PersistenceConstants.FLASH)
    private String message;

    @SessionState
    private BookingSession session;

    @Property
    private Booking booking = new Booking();

    @Component
    private Form form;

    @InjectPage
    private BookPage bookPage;

    @Inject
    private JpaService jpa;

    @Inject
    private Logger logger;

    public DecimalFormat getCurrencyFormat() {
        return AppModule.CURRENCY_FORMAT;
    }

    private int flowId;

    private boolean reviseSubmitted;
    private boolean cancelSubmitted;

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    void onSelectedFromRevise() {
        reviseSubmitted = true;
    }

    void onSelectedFromCancel() {
        cancelSubmitted = true;
    }

    Object onSuccess() {
        if(reviseSubmitted) {
            bookPage.setFlowId(flowId);
            return bookPage;
        }
        if(cancelSubmitted) {
            session.finishFlow(flowId);
            return MainPage.class;
        }
        jpa.getEntityManager().persist(booking);
        message = String.format("Thank you, %s, your confimation number for %s is %s",
                session.getUser().getName(), booking.getHotel().getName(), booking.getId());
        logger.info(String.format("New booking: %s for %s",
                booking.getId(), session.getUser().getUsername()));
        session.loadBookings(jpa.getEntityManager());
        jpa.getEntityManager().getTransaction().commit();
        session.finishFlow(flowId);
        return MainPage.class;
    }

    void onActivate(int flowId) {
        this.flowId = flowId;
        booking = session.getBookingForFlow(flowId);
    }

    int onPassivate() {
        return flowId;
    }

}
