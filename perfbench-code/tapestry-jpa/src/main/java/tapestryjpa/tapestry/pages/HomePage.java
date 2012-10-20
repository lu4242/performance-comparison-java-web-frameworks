package tapestryjpa.tapestry.pages;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import tapestryjpa.web.BookingSession;
import tapestryjpa.entity.User;
import tapestryjpa.tapestry.components.Template;
import tapestryjpa.tapestry.services.JpaEntityManagerFactoryService;

@Import(stylesheet="context:css/screen.css")
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

    @Property
    @SessionState(create=false)
    private BookingSession session;

    //@Inject
    //private JpaService jpa;
    
    @Inject
    private JpaEntityManagerFactoryService jpaEmf;

    @Inject
    private Logger logger;

    Object onSuccess() {
        //EntityManager em = jpa.getEntityManager();
        EntityManager em = jpaEmf.createEntityManager();
        em.getTransaction().begin();
        try
        {
            Query query = em.createQuery("select u from User u"
                    + " where u.username = :username and u.password = :password");
            query.setParameter("username", username);
            query.setParameter("password", password);
            List<User> users = query.getResultList();
            if (users.size() == 0) {
                if (Template.LOG_ENABLED)
                {
                    logger.error("Login failed");
                }
                message = "Login failed";
                return null;
            }
            User user = users.get(0);
            session = new BookingSession();
            session.setUser(user);
            if (Template.LOG_ENABLED)
            {
                logger.info("Login succeeded");
            }
            message = "Welcome, " + user.getUsername();
        }
        finally
        {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            em.close();
        }
        return MainPage.class;
    }
}
