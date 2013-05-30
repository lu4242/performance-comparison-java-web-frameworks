/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package springmvcjpa.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import springmvcjpa.entity.Booking;
import springmvcjpa.service.BookingRepository;

/**
 *
 * @author lu4242
 */
@Controller
@SessionAttributes(types={BookingSession.class, HotelBean.class, BookingBean.class}, 
                   value={BookingSession.NAME, HotelBean.NAME, BookingBean.NAME})
@RequestMapping(value="/confirm")
public class ConfirmController
{
    protected static final Logger logger = LoggerFactory.getLogger(ConfirmController.class);
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @RequestMapping(method=RequestMethod.POST, params="confirm")
    public ModelAndView confirm(
            @ModelAttribute(BookingSession.NAME) BookingSession bookingSession,
            @ModelAttribute(BookingBean.NAME) BookingBean bookingBean,
            ModelAndView modelAndView)
    {
        Booking booking = bookingBean.getBooking();
        bookingRepository.create(booking);
        modelAndView.setViewName("main");
        modelAndView.addObject("infoMessage", "Thank you, "+booking.getUser().getName()+
                    ", your confimation number for "+booking.getHotel().getName()+" is "+ booking.getId());
        
        bookingBean.setBookings(bookingRepository.queryBookings(bookingSession.getUser().getUsername()));
        if (BookingApplication.LOG_ENABLED)
        {
            logger.info("New booking: "+booking.getId()+" for "+booking.getUser().getUsername());
        }
        return modelAndView;
    }    
    
    @RequestMapping(method=RequestMethod.POST, params="revise")
    public String revise()
    {
		return "book";
    }
    
    @RequestMapping(method=RequestMethod.POST, params="cancel")
    public String cancel()
    {
        return "main";
    }
}
