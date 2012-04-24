package tapestryjpa.tapestry.components;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import tapestryjpa.web.BookingSession;
import tapestryjpa.tapestry.pages.HomePage;

@IncludeStylesheet("context:css/screen.css")
public class Template {

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
