package wicketjpa.wicket;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import wicketjpa.entity.User;

public class RegisterPage extends WebPage {    

    public RegisterPage() {        
        add(new RegisterForm("form"));
        setStatelessHint(true);
    }

    private class RegisterForm extends StatelessForm<User> {

        private User user = new User();        
        private FormComponent username;

        public RegisterForm(String id) {
            super(id);
            setModel(new CompoundPropertyModel<User>(user));
            username = new TextField("username").setRequired(true);
            add(new EditBorder("usernameBorder", username, true));
            add(new EditBorder("nameBorder", new TextField("name").setRequired(true), true));
            FormComponent passwordField = new PasswordTextField("password").setRequired(true);            
            add(new EditBorder("passwordBorder", passwordField));
            FormComponent verifyField = new PasswordTextField("verify", Model.of(""));
            add(new EditBorder("verifyBorder", verifyField));
            add(new EqualPasswordInputValidator(passwordField, verifyField));
            add(new BookmarkablePageLink<Void>("cancel", HomePage.class));
        }

        @Override
        protected void onSubmit() {
            EntityManager em = JpaRequestCycleListener.getEntityManager();
            Query query = em.createQuery("select u.username from User u where u.username = :username");
            query.setParameter("username", user.getUsername());
            if (query.getResultList().size() > 0) {
                username.error("Username " + user.getUsername() + " already exists");
                return;
            }
            em.persist(user);
            getSession().info("Successfully registered as " + user.getUsername());
            setResponsePage(HomePage.class);
        }        
    }
}
