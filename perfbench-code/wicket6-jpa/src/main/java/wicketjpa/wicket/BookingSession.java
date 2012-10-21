package wicketjpa.wicket;

import java.util.List;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import wicketjpa.entity.Booking;
import wicketjpa.entity.Hotel;
import wicketjpa.entity.User;

public class BookingSession extends WebSession {

    private User user;
    private String searchString;
    private List<Hotel> hotels;
    private List<Booking> bookings;
    private int pageSize = 10;
    private int page;

    public static BookingSession get() {
        return (BookingSession) WebSession.get();
    }

    public BookingSession(Request request) {
        super(request);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
