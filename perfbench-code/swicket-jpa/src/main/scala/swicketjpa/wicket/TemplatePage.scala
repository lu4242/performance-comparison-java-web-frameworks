package swicketjpa.wicket

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.model.PropertyModel
import org.slf4j.LoggerFactory
import swicketjpa.entity.Booking

object TemplatePage {
  val logger = LoggerFactory.getLogger(classOf[TemplatePage])
}

class TemplatePage extends WebPage {

  def logger = TemplatePage.logger

  add(new Label("userName", new PropertyModel(this, "session.user.name")))
  add(new BookmarkablePageLink("search", classOf[MainPage]))
  add(new BookmarkablePageLink("settings", classOf[PasswordPage]))
  add(new BookmarkablePageLink("logout", classOf[LogoutPage]))

  def getEntityManager() = getRequestCycle().asInstanceOf[JpaRequestCycle].getEntityManager()

  def endConversation() = getRequestCycle().asInstanceOf[JpaRequestCycle].endConversation()

  def getBookingSession() = getSession().asInstanceOf[BookingSession]

  def loadBookings() = {
    val query = getEntityManager().createQuery("select b from Booking b"
      + " where b.user.username = :username order by b.checkinDate")
    query.setParameter("username", getBookingSession().user.username)
    getBookingSession().bookings = query.getResultList().asInstanceOf[java.util.List[Booking]]
  }

}
