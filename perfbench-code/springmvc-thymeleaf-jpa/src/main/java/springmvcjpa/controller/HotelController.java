/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package springmvcjpa.controller;

import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import springmvcjpa.entity.Booking;
import springmvcjpa.entity.Hotel;
import springmvcjpa.service.HotelRepository;

/**
 *
 * @author lu4242
 */
@Controller
@SessionAttributes(types={BookingSession.class, HotelBean.class, BookingBean.class}, 
                   value={BookingSession.NAME, HotelBean.NAME, BookingBean.NAME})
public class HotelController
{
    @Autowired
    private HotelRepository hotelRepository;
    
    @RequestMapping(value="/hotel", method=RequestMethod.GET)
	public ModelAndView hotel(
            @RequestParam(required=true) long id,
            @ModelAttribute(BookingSession.NAME) BookingSession bookingSession,
            @ModelAttribute(BookingBean.NAME) BookingBean bookingBean)
	{
        Hotel hotel = hotelRepository.queryFindById(id);
        Booking booking = new Booking(hotel, bookingSession.getUser());
        Calendar calendar = Calendar.getInstance();
        booking.setCheckinDate(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        booking.setCheckoutDate(calendar.getTime());
        bookingBean.setBooking(booking);

        ModelAndView modelAndView = new ModelAndView("hotel");
        modelAndView.addObject("hotel", hotel);
		return modelAndView;
	}
    
    @RequestMapping(value="/hotel", method=RequestMethod.POST, params="book")
    public ModelAndView book(@ModelAttribute(BookingBean.NAME) BookingBean bookingBean,
                            ModelAndView modelAndView)
    {
        modelAndView.getModelMap().addAttribute("bookForm", new BookForm(bookingBean.getBooking()));
        modelAndView.setViewName("book");
		return modelAndView;
    }

    @RequestMapping(value="/hotel", method=RequestMethod.POST, params="cancel")
    public String cancel()
    {
        return "main";
    }
}
