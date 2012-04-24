package grocketjpa.wicket

import javax.persistence.EntityManager
import javax.persistence.Query
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.markup.html.form.PasswordTextField
import org.apache.wicket.markup.html.form.StatelessForm
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.model.CompoundPropertyModel
import org.apache.wicket.model.Model
import grocketjpa.entity.User

class RegisterPage extends WebPage {

    RegisterPage() {
        add new RegisterForm("form")
        statelessHint = true
    }

    static class RegisterForm extends StatelessForm {

        User user = new User()
        FormComponent username

        RegisterForm(id) {
            super(id)            
            setModel new CompoundPropertyModel(user)
            username = new TextField("username").setRequired(true)
            add new EditBorder("usernameBorder", username, true)
            add new EditBorder("nameBorder", new TextField("name").setRequired(true), true)
            def passwordField = new PasswordTextField("password").setRequired(true)
            add new EditBorder("passwordBorder", passwordField)
            def verifyField = new PasswordTextField("verify", new Model(""))
            add new EditBorder("verifyBorder", verifyField)
            add new EqualPasswordInputValidator(passwordField, verifyField)
            add new BookmarkablePageLink("cancel", HomePage.class)
        }
        
        void onSubmit() {
            def em = JpaRequestCycle.get().entityManager
            def query = em.createQuery("select u.username from User u where u.username = :username")
            query.setParameter("username", user.username)
            if (query.resultList.size() > 0) {
                username.error("Username " + user.username + " already exists")
                return
            }
            em.persist(user)
            session.info("Successfully registered as " + user.username)
            setResponsePage HomePage.class
        }
    }
}


