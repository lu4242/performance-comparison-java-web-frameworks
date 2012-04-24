package grocketjpa.wicket

import java.util.Calendar
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.model.CompoundPropertyModel
import grocketjpa.entity.Booking
import grocketjpa.entity.Hotel

class HotelPage extends TemplatePage {

    HotelPage(Hotel hotel) {
        setDefaultModel new CompoundPropertyModel(hotel)
        add new Label("name")
        add new Label("address")
        add new Label("city")
        add new Label("state")
        add new Label("zip")
        add new Label("country")
        add new Label("price")
        def form = new Form("form") {
            void onSubmit() {
                def booking = new Booking(hotel: hotel, user: session.getUser())
                def calendar = Calendar.getInstance()
                booking.checkinDate = calendar.time
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                booking.checkoutDate = calendar.time
                setResponsePage new BookPage(booking)
            }
        }
        add form
        form.add new BookmarkablePageLink("cancel", MainPage.class)
    }
}


