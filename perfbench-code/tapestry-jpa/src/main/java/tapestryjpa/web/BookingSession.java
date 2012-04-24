package tapestryjpa.web;

import tapestryjpa.entity.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class BookingSession implements Serializable {

    private User user;
    private String searchString;
    private List<Hotel> hotels;
    private List<Booking> bookings;
    private int pageSize = 10;
    private int page;

    private int nextFlowId = 1;
    private Map<Integer, Booking> bookingFlows = new ConcurrentHashMap<Integer, Booking>();

    public synchronized int startFlow(Booking booking) {
        final int flowId = nextFlowId++;
        bookingFlows.put(flowId, booking);
        return flowId;
    }

    public Booking getBookingForFlow(int flowId) {
        return bookingFlows.get(flowId);
    }

    public void finishFlow(int flowId) {
        bookingFlows.remove(flowId);
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void loadBookings(EntityManager em) {
        Query query = em.createQuery("select b from Booking b"
                + " where b.user.username = :username order by b.checkinDate");
        query.setParameter("username", user.getUsername());
        bookings = query.getResultList();
    }

}
