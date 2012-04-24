package wicketjpa.wicket;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.CompoundPropertyModel;
import wicketjpa.entity.Booking;

public class ConfirmPage extends TemplatePage {

    public ConfirmPage(final Booking booking) {
        setDefaultModel(new CompoundPropertyModel(booking));
        add(new Label("hotel.name"));
        add(new Label("hotel.address"));
        add(new Label("hotel.city"));
        add(new Label("hotel.state"));
        add(new Label("hotel.zip"));
        add(new Label("hotel.country"));
        add(new Label("total"));
        add(new Label("checkinDate"));
        add(new Label("checkoutDate"));
        add(new Label("creditCard"));
        Form form = new Form("form") {
            @Override
            public void onSubmit() {                
                getEntityManager().persist(booking);
                BookingSession session = getBookingSession();
                session.info(String.format("Thank you, %s, your confimation number for %s is %s",
                        session.getUser().getName(), booking.getHotel().getName(), booking.getId()));
                logger.info("New booking: {} for {}", booking.getId(), session.getUser().getUsername());
                loadBookings();
                endConversation();                
                setResponsePage(MainPage.class);
            }
        };
        add(form);
        form.add(new Button("revise") {
            @Override
            public void onSubmit() {
                setResponsePage(new BookPage(booking));
            }
        }.setDefaultFormProcessing(false));
        form.add(new BookmarkablePageLink("cancel", MainPage.class));
    }
}
