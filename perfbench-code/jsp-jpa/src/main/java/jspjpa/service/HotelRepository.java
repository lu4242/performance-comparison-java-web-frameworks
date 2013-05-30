/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.service;

import java.util.List;
import jspjpa.entity.Hotel;

/**
 *
 * @author lu4242
 */
public interface HotelRepository
{
    public List<Hotel> queryHotels(String searchString, int firstResult, int maxResults);
    
    public Hotel queryFindById(long id);
}
