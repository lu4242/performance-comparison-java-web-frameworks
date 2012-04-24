package tapestryjpa.tapestry.pages;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import tapestryjpa.web.BookingSession;
import tapestryjpa.entity.User;
import tapestryjpa.tapestry.services.JpaService;

@IncludeStylesheet("context:css/screen.css")
public class HomePage {

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String message;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String username;

    @Property
    private String password;

    @Component
    private Form form;

    @SessionState(create=false)
    private BookingSession session;

    @Inject
    private JpaService jpa;

    @Inject
    private Logger logger;

    Object onSuccess() {
        EntityManager em = jpa.getEntityManager();
        Query query = em.createQuery("select u from User u"
                + " where u.username = :username and u.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        List<User> users = query.getResultList();
        if (users.size() == 0) {
            logger.error("Login failed");
            message = "Login failed";
            return null;
        }
        User user = users.get(0);
        session = new BookingSession();
        session.setUser(user);
        logger.info("Login succeeded");
        message = "Welcome, " + user.getUsername();
        return MainPage.class;
    }

}
