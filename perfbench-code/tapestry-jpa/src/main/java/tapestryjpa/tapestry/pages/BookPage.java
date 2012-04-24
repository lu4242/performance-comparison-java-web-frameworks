package tapestryjpa.tapestry.pages;

import java.text.DecimalFormat;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import tapestryjpa.entity.Booking;
import tapestryjpa.tapestry.services.AppModule;
import tapestryjpa.web.BookingSession;

public class BookPage {

    @SessionState
    private BookingSession session;

    private int flowId;

    @Property
    private Booking booking = new Booking();

    @Component
    private Form form;

    @InjectPage
    private ConfirmPage confirmPage;

    public DecimalFormat getCurrencyFormat() {
        return AppModule.CURRENCY_FORMAT;
    }

    private boolean cancelSubmitted;

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }
    
    void onSelectedFromCancel() {
        cancelSubmitted = true;
    }

    Object onSuccess() {
        if(cancelSubmitted) {
            session.finishFlow(flowId);
            return MainPage.class;
        }
        confirmPage.setFlowId(flowId);
        return confirmPage;
    }

    void onActivate(int flowId) {        
        this.flowId = flowId;
        booking = session.getBookingForFlow(flowId);        
    }

    int onPassivate() {
        return flowId;
    }

}
