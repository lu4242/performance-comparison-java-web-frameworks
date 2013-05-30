/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.controller;

import java.io.Serializable;
import java.util.List;
import jspjpa.entity.Booking;

/**
 *
 * @author lu4242
 */
public class BookingBean implements Serializable {
    
    public static final String NAME = "bookingBean";
    
    private Booking booking;
    
    private List<Booking> bookings;

    public BookingBean()
    {
    }
    
    /**
     * @return the booking
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * @param booking the booking to set
     */
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    
    public List<Booking> getBookings()
    {
        return bookings;
    }

    /**
     * @param bookings the bookings to set
     */
    public void setBookings(List<Booking> bookings)
    {
        this.bookings = bookings;
    }
    
}
