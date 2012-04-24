package grocketjpa.wicket

import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.Button
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.model.CompoundPropertyModel
import grocketjpa.entity.Booking

class ConfirmPage extends TemplatePage {

    def booking

    ConfirmPage(Booking booking) {
        this.booking = booking
        setDefaultModel new CompoundPropertyModel(booking)
        add new Label("hotel.name")
        add new Label("hotel.address")
        add new Label("hotel.city")
        add new Label("hotel.state")
        add new Label("hotel.zip")
        add new Label("hotel.country")
        add new Label("total")
        add new Label("checkinDate")
        add new Label("checkoutDate")
        add new Label("creditCard")
        def form = new Form("form") {
            void onSubmit() {
                getEntityManager().persist(booking)
                session.info(String.format("Thank you, %s, your confimation number for %s is %s",
                        session.user.name, booking.hotel.name, booking.id))
                // logger.info("New booking: {} for {}", booking.id, session.user.username)
                loadBookings()
                endConversation()
                setResponsePage MainPage.class
            }
        }
        add form
        form.add(new Button("revise") {            
            void onSubmit() {
                setResponsePage new BookPage(booking)
            }
        }.setDefaultFormProcessing(false))
        form.add new BookmarkablePageLink("cancel", MainPage.class)
    }
}


