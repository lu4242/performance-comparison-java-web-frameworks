package tapestryjpa.tapestry.components;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import tapestryjpa.web.BookingSession;
import tapestryjpa.tapestry.pages.HomePage;

@Import(stylesheet="context:css/screen.css")
public class Template {
 
    public static final boolean LOG_ENABLED = false;
 
    @Property
    @SessionState(create=false)
    private BookingSession session;

    @Inject
    private Request request;

    Object onActionFromLogout() {
        request.getSession(false).invalidate();
        return HomePage.class;
    }
    
}
