package swicketjpa.wicket

import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form._
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.model.CompoundPropertyModel
import swicketjpa.entity.Booking

class ConfirmPage(booking: Booking) extends TemplatePage {
  setDefaultModel(new CompoundPropertyModel(booking))
  add(new Label("hotel.name"))
  add(new Label("hotel.address"))
  add(new Label("hotel.city"))
  add(new Label("hotel.state"))
  add(new Label("hotel.zip"))
  add(new Label("hotel.country"))
  add(new Label("total"))
  add(new Label("checkinDate"))
  add(new Label("checkoutDate"))
  add(new Label("creditCard"))
  add(new Form("form") {
    add(new Button("revise") {
      override def onSubmit = setResponsePage(new BookPage(booking))
    }.setDefaultFormProcessing(false))
    add(new BookmarkablePageLink("cancel", classOf[MainPage]))
    override def onSubmit = {
      getEntityManager().persist(booking)
      val session = getBookingSession()
      session.info(String.format("Thank you, %s, your confimation number for %s is %s",
          session.user.name, booking.hotel.name, booking.id.toString))
      logger.info("New booking: {} for {}", booking.id, session.user.username)
      loadBookings()
      endConversation()
      setResponsePage(classOf[MainPage])
    }
  })
}
