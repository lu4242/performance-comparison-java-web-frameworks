package swicketjpa.wicket

import java.util.Calendar
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.model.CompoundPropertyModel
import swicketjpa.entity.Booking
import swicketjpa.entity.Hotel

class HotelPage(hotel: Hotel) extends TemplatePage {
  setDefaultModel(new CompoundPropertyModel(hotel))
  add(new Label("name"))
  add(new Label("address"))
  add(new Label("city"))
  add(new Label("state"))
  add(new Label("zip"))
  add(new Label("country"))
  add(new Label("price"))
  add(new Form("form") {
    add(new BookmarkablePageLink("cancel", classOf[MainPage]))
    override def onSubmit = {
      val booking = new Booking(hotel, getBookingSession().user)
      val calendar = Calendar.getInstance()
      booking.checkinDate = calendar.getTime()
      calendar.add(Calendar.DAY_OF_MONTH, 1)
      booking.checkoutDate = calendar.getTime()
      setResponsePage(new BookPage(booking))
    }
  })
}
