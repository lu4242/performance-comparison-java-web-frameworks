package swicketjpa.wicket

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.form._
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.model.Model
import org.slf4j.LoggerFactory
import swicketjpa.entity.User

object HomePage {
  val logger = LoggerFactory.getLogger(classOf[HomePage])
}

class HomePage extends WebPage {  

  def logger = HomePage.logger
  
  add(new StatelessForm("form") {
    val username = new TextField("username", new Model(""))
    val password = new PasswordTextField("password", new Model(""))
    add(username)
    add(password.setRequired(false))
    add(new BookmarkablePageLink("register", classOf[RegisterPage]))
    add(new FeedbackPanel("messages"))

    override def onSubmit: Unit = {
      val em = JpaRequestCycle.get().getEntityManager()
      val query = em.createQuery("select u from User u"
        + " where u.username = :username and u.password = :password")
      query.setParameter("username", username.getInput())
      query.setParameter("password", password.getInput())
      val users = query.getResultList().asInstanceOf[java.util.List[User]]
      if (users.size == 0) {
        logger.error("Login failed")
        error("Login failed")
        return
      }
      val user = users.get(0)
      val session = BookingSession.get()
      session.user = user
      session.bind();
      logger.info("Login succeeded");
      session.info("Welcome, " + user.username)
      setResponsePage(classOf[MainPage])
    }

  })

  setStatelessHint(true)  
  
}
