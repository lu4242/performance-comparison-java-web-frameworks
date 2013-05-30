/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.service;

import java.util.List;
import jspjpa.entity.Booking;

/**
 *
 * @author lu4242
 */
public interface BookingRepository
{
    public List<Booking> queryBookings(String username);
    
    public Booking find(Long id);
    
    public void cancel(Long id);
            
    public void create(Booking b);
}
