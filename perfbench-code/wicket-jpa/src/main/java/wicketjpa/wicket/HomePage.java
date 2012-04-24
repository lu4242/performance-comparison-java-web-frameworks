package wicketjpa.wicket;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wicketjpa.entity.User;

public class HomePage extends WebPage {

    protected static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    public HomePage() {
        add(new LoginForm("form"));
        setStatelessHint(true);
    }

    private static class LoginForm extends StatelessForm {

        private TextField username = new TextField("username", new Model(""));
        private TextField password = new PasswordTextField("password", new Model(""));

        public LoginForm(String id) {
            super(id);            
            add(username);            
            add(password.setRequired(false));
            add(new BookmarkablePageLink("register", RegisterPage.class));
            add(new FeedbackPanel("messages"));
        }

        @Override
        protected void onSubmit() {
            EntityManager em = JpaRequestCycle.get().getEntityManager();
            Query query = em.createQuery("select u from User u"
                    + " where u.username = :username and u.password = :password");
            query.setParameter("username", username.getInput());
            query.setParameter("password", password.getInput());
            List<User> users = query.getResultList();
            if (users.size() == 0) {
                logger.error("Login failed");
                error("Login failed");
                return;
            }
            User user = users.get(0);
            BookingSession session = BookingSession.get();            
            session.setUser(user);
            session.bind();
            logger.info("Login succeeded");
            session.info("Welcome, " + user.getUsername());
            setResponsePage(MainPage.class);
        }        
    }
}
