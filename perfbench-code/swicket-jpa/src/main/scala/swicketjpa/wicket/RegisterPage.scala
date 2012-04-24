package swicketjpa.wicket

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.markup.html.form.PasswordTextField
import org.apache.wicket.markup.html.form.StatelessForm
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.model.CompoundPropertyModel
import org.apache.wicket.model.Model
import swicketjpa.entity.User

class RegisterPage extends WebPage {

  add(new StatelessForm("form") {
    val user = new User()
    setDefaultModel(new CompoundPropertyModel(user))
    val username: FormComponent[String] = new TextField("username").setRequired(true)
    add(new EditBorder("usernameBorder", username, true))
    add(new EditBorder("nameBorder", new TextField("name").setRequired(true), true))
    val passwordField = new PasswordTextField("password").setRequired(true)
    add(new EditBorder("passwordBorder", passwordField))
    val verifyField = new PasswordTextField("verify", new Model(""))
    add(new EditBorder("verifyBorder", verifyField))
    add(new EqualPasswordInputValidator(passwordField, verifyField))
    add(new BookmarkablePageLink("cancel", classOf[HomePage]))

    override def onSubmit: Unit = {
      val em = JpaRequestCycle.get().getEntityManager()
      val query = em.createQuery("select u.username from User u where u.username = :username")
      query.setParameter("username", user.username)
      if (query.getResultList().size() > 0) {
        username.error("Username " + user.username + " already exists")
        return
      }
      em.persist(user)
      getSession().info("Successfully registered as " + user.username)
      setResponsePage(classOf[HomePage])
    }

  })

  setStatelessHint(true)

}
