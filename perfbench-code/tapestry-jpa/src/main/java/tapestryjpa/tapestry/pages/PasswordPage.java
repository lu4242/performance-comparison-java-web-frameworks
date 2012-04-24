package tapestryjpa.tapestry.pages;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import tapestryjpa.entity.User;
import tapestryjpa.tapestry.services.JpaService;
import tapestryjpa.web.BookingSession;

public class PasswordPage {

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String message;

    @Property
    @SessionState
    private BookingSession session;

    @Property
    private User user;

    @Property
    private String verify;

    @Inject
    private JpaService jpa;

    private boolean cancelSubmitted;

    void onSelectedFromCancel() {
        cancelSubmitted = true;
    }

    void onActivate() {
        user = session.getUser();
    }

    Object onSuccess() {
        if(cancelSubmitted) {
            return MainPage.class;
        }
        jpa.getEntityManager().merge(user);
        jpa.getEntityManager().getTransaction().commit();
        message = "Password updated";
        return MainPage.class;
    }

}
