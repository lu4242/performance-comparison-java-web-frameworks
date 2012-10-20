package tapestryjpa.tapestry.pages;

import java.text.DecimalFormat;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import tapestryjpa.entity.Booking;
import tapestryjpa.tapestry.services.AppModule;
import tapestryjpa.web.BookingSession;

public class BookPage {

    @Property
    @SessionState
    private BookingSession session;

    private int flowId;

    @Property
    private Booking booking = new Booking();

    @Component
    private Form form;

    @InjectPage
    private ConfirmPage confirmPage;
    
    //Ajax Server Side Validation
	@Inject
	private Request request;    
    
	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

    @Property
    private String creditCardMsg;

    @InjectComponent
	private Zone creditCardMsgZone;
    
    @Property
    private String creditCardNameMsg;

    @InjectComponent
	private Zone creditCardNameMsgZone;
    
    void onValidateCreditCardNumber()
    {
        String creditCardNumber = request.getParameter("param");
        if (creditCardNumber == null)
        {
            creditCardMsg = "Value is required";
        }
        else if (creditCardNumber.length() != 16)
        {
            creditCardMsg = "Credit card number must 16 digits long";
        }
        else
        {
            creditCardMsg = "";
        }
		if (request.isXHR())
        {
			ajaxResponseRenderer.addRender("creditCardMsgZone", creditCardMsgZone);
		}        
    }
    
    void onValidateCreditCardName()
    {
        String creditCardName = request.getParameter("param");
        if (creditCardName == null)
        {
            creditCardNameMsg = "Value is required";
        }
        else if (creditCardName.length() < 3)
        {
            creditCardNameMsg = "Value is required";
        }
        else
        {
            creditCardNameMsg = "";
        }
		if (request.isXHR())
        {
			ajaxResponseRenderer.addRender("creditCardNameMsgZone", creditCardNameMsgZone);
		}
    }

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
