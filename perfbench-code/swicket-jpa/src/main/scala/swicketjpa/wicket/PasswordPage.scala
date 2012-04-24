package swicketjpa.wicket

import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.PasswordTextField
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.model.CompoundPropertyModel
import org.apache.wicket.model.Model

class PasswordPage extends TemplatePage {

  add(new Form("form") {
    val user = getBookingSession().user
    setDefaultModel(new CompoundPropertyModel(user))
    val passwordField = new PasswordTextField("password")
    add(new EditBorder("passwordBorder", passwordField))
    val verifyField = new PasswordTextField("verify", new Model(""))
    add(new EditBorder("verifyBorder", verifyField))
    add(new EqualPasswordInputValidator(passwordField, verifyField))
    add(new BookmarkablePageLink("cancel", classOf[MainPage]))

    override def onSubmit = {
      getEntityManager().merge(user)
      getSession().info("Password updated")
      setResponsePage(classOf[MainPage])
    }

  })

}
