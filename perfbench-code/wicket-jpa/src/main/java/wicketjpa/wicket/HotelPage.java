package wicketjpa.wicket;

import java.util.Calendar;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.CompoundPropertyModel;
import wicketjpa.entity.Booking;
import wicketjpa.entity.Hotel;

public class HotelPage extends TemplatePage {

    public HotelPage(final Hotel hotel) {
        setDefaultModel(new CompoundPropertyModel(hotel));
        add(new Label("name"));
        add(new Label("address"));
        add(new Label("city"));
        add(new Label("state"));
        add(new Label("zip"));
        add(new Label("country"));
        add(new Label("price"));
        Form form = new Form("form") {
            @Override
            public void onSubmit() {                
                Booking booking = new Booking(hotel, getBookingSession().getUser());
                Calendar calendar = Calendar.getInstance();
                booking.setCheckinDate(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                booking.setCheckoutDate(calendar.getTime());
                setResponsePage(new BookPage(booking));
            }
        };
        add(form);        
        form.add(new BookmarkablePageLink("cancel", MainPage.class));
    }    
}
