/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package springmvcjpa.controller;

import java.io.Serializable;
import springmvcjpa.entity.User;

public class BookingSession implements Serializable
{
    public static final String NAME = "bookingSession";
    
    private User user;
    
    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
