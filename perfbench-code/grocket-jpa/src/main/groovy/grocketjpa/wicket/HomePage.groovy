package grocketjpa.wicket

import java.util.List
import javax.persistence.EntityManager
import javax.persistence.Query
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.form.PasswordTextField
import org.apache.wicket.markup.html.form.StatelessForm
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.model.Model
import org.slf4j.LoggerFactory
import grocketjpa.entity.User

class HomePage extends WebPage {

    HomePage() {
        add new LoginForm("form")
        statelessHint = true
    }

    static class LoginForm extends StatelessForm {

        static final logger = LoggerFactory.getLogger(HomePage.class)

        def username = new TextField("username", new Model(""))
        def password = new PasswordTextField("password", new Model(""))

        LoginForm(id) {
            super(id)
            add username
            add password.setRequired(false)
            add new BookmarkablePageLink("register", RegisterPage.class)
            add new FeedbackPanel("messages")
        }
        
        void onSubmit() {
            def em = JpaRequestCycle.get().entityManager
            def query = em.createQuery("select u from User u"
                    + " where u.username = :username and u.password = :password")
            query.setParameter("username", username.input)
            query.setParameter("password", password.input)
            def users = query.resultList
            if (users.size() == 0) {
                logger.error "Login failed"
                error "Login failed"
                return
            }
            def user = users.get(0)
            def session = BookingSession.get()
            session.user = user
            session.bind()
            logger.info "Login succeeded"
            session.info "Welcome, " + user.username
            setResponsePage MainPage.class
        }
        
    }
	
}

