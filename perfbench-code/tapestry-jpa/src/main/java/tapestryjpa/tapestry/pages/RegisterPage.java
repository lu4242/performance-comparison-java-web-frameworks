package tapestryjpa.tapestry.pages;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import tapestryjpa.entity.User;
import tapestryjpa.tapestry.services.JpaService;

@IncludeStylesheet("context:css/screen.css")
public class RegisterPage {

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String message;

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
        user = new User();
    }

    Object onSuccess() {
        if(cancelSubmitted) {
            return MainPage.class;
        }
        EntityManager em = jpa.getEntityManager();
        Query query = em.createQuery("select u.username from User u where u.username = :username");
        query.setParameter("username", user.getUsername());
        if (query.getResultList().size() > 0) {
            message = "Username " + user.getUsername() + " already exists";
            return null;
        }
        em.persist(user);
        em.getTransaction().commit();
        message = "Successfully registered as " + user.getUsername();
        return MainPage.class;
    }

}
