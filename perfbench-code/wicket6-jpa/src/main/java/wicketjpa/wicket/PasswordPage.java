package wicketjpa.wicket;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import wicketjpa.entity.User;

public class PasswordPage extends TemplatePage {

    public PasswordPage() {
        add(new PasswordForm("form"));
    }

    private class PasswordForm extends Form<User> {

        private User user = getBookingSession().getUser();

        public PasswordForm(String id) {
            super(id);
            setModel(new CompoundPropertyModel<User>(user));
            FormComponent passwordField = new PasswordTextField("password");            
            add(new EditBorder("passwordBorder", passwordField));
            FormComponent verifyField = new PasswordTextField("verify", Model.of(""));
            add(new EditBorder("verifyBorder", verifyField));            
            add(new EqualPasswordInputValidator(passwordField, verifyField));            
            add(new BookmarkablePageLink<Void>("cancel", MainPage.class));
        }

        @Override
        protected void onSubmit() {            
            getEntityManager().merge(user);
            getSession().info("Password updated");
            setResponsePage(MainPage.class);
        }        
    }    
}
