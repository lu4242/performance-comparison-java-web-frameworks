package grocketjpa.wicket

import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.markup.html.form.PasswordTextField
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.model.CompoundPropertyModel
import org.apache.wicket.model.Model
import grocketjpa.entity.User

class PasswordPage extends TemplatePage {

    PasswordPage() {
        add new PasswordForm("form", session.user)
    }

    static class PasswordForm extends Form {

        User user

        public PasswordForm(id, user) {
            super(id)
            this.user = user
            setDefaultModel new CompoundPropertyModel(user)
            def passwordField = new PasswordTextField("password")
            add new EditBorder("passwordBorder", passwordField)
            def verifyField = new PasswordTextField("verify", new Model(""))
            add new EditBorder("verifyBorder", verifyField)
            add new EqualPasswordInputValidator(passwordField, verifyField)
            add new BookmarkablePageLink("cancel", MainPage.class)
        }
        
        void onSubmit() {
            requestCycle.entityManager.merge(user)
            session.info("Password updated")
            setResponsePage MainPage.class
        }
    }

}


