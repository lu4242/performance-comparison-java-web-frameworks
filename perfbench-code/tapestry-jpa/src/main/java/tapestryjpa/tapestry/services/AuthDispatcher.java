package tapestryjpa.tapestry.services;

import java.io.IOException;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Dispatcher;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import tapestryjpa.web.BookingSession;

public class AuthDispatcher implements Dispatcher {

    private ApplicationStateManager asm;

    public AuthDispatcher(ApplicationStateManager asm) {
        this.asm = asm;
    }

    @Override
    public boolean dispatch(Request request, Response response) throws IOException {
        String path = request.getPath();        
        if (path.equals("/homepage") || path.equals("/homepage.form")
                || path.startsWith("/assets/")
                || path.equals("/registerpage")) {
            return false;
        }
        if (asm.exists(BookingSession.class)) {
            BookingSession session = asm.get(BookingSession.class);            
            if (session.getUser() != null) {
                if (path.equals("/")) {
                    response.sendRedirect(request.getContextPath() + "/mainpage");
                    return true;
                }
                return false;
            }            
        }
        response.sendRedirect(request.getContextPath() + "/homepage");
        return true;
    }
    
}
