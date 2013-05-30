/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package springmvcjpa.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import springmvcjpa.entity.Booking;
import springmvcjpa.entity.Hotel;
import springmvcjpa.service.BookingRepository;
import springmvcjpa.service.HotelRepository;

/**
 *
 * @author Leonardo Uribe
 */
@Controller
@SessionAttributes(types={BookingSession.class, HotelBean.class, BookingBean.class}, 
                   value={BookingSession.NAME, HotelBean.NAME, BookingBean.NAME})
public class MainController
{
    @Autowired
    private HotelRepository hotelRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession(false);
        if (session != null)
        {
            session.invalidate();
        }
        return "redirect:home.do";
    }
    
    @RequestMapping(value="/main", method=RequestMethod.GET)
    public String show()
    {
        return "main";
    }
    
    /**
     * Normal submit search
     */
    @RequestMapping(value="/main", method=RequestMethod.POST)
	public String search(
            @ModelAttribute(HotelBean.NAME) HotelBean hotelBean, 
            @ModelAttribute(BookingBean.NAME) BookingBean bookingBean,
            ModelAndView modelAndView, BindingResult bindingResult)
	{
        hotelBean.setPage(0);
        queryHotels(hotelBean);
        
		return "main";
	}
    
    /**
     * Ajax search (used)
     * 
     * @param hotelBean
     * @param modelAndView
     * @param bindingResult
     * @return 
     */
    @RequestMapping(value="/main", method=RequestMethod.POST, headers="X-Requested-With=XMLHttpRequest")
	public ModelAndView searchAjax(
            @ModelAttribute(HotelBean.NAME) HotelBean hotelBean, 
            ModelAndView modelAndView, BindingResult bindingResult)
	{
        hotelBean.setPage(0);
        queryHotels(hotelBean);
        
        modelAndView.setViewName("hotelstable");
        return modelAndView;
	}
    
    @RequestMapping(value="/main", method=RequestMethod.POST, params="cancelId")
	public ModelAndView cancel(
            @RequestParam(required=true) Long cancelId,
            @ModelAttribute(HotelBean.NAME) HotelBean hotelBean, 
            @ModelAttribute(BookingBean.NAME) BookingBean bookingBean,
            @ModelAttribute(BookingSession.NAME) BookingSession bookingSession,
            ModelAndView modelAndView, BindingResult bindingResult)
	{
        Booking cancelled = bookingRepository.find(cancelId);
        if (cancelled != null)
        {
            bookingRepository.cancel(cancelId);
            bookingBean.setBookings(bookingRepository.queryBookings(bookingSession.getUser().getUsername()));
            modelAndView.addObject("infoMessage", "Booking cancelled for confirmation number "+cancelled.getId());
        }
        return modelAndView;
	}
    
    @RequestMapping(value="/main", method=RequestMethod.GET, params="action")
	public String nextPage(
            @RequestParam(required=true) String action,
            @ModelAttribute(HotelBean.NAME) HotelBean hotelBean,
            @ModelAttribute(BookingBean.NAME) BookingBean bookingBean,
            ModelAndView modelAndView, BindingResult bindingResult)
	{
        if ("nextPage".equals(action))
        {
            nextPage(hotelBean);
        }
		return "main";
    }
    
    public void nextPage(HotelBean hotelBean) {
        hotelBean.setPage(hotelBean.getPage()+1);
        queryHotels(hotelBean);
    }
    
    public boolean isNextPageAvailable(HotelBean hotelBean)
    {
        return hotelBean.isNextPageAvailable();
    }

    private void queryHotels(HotelBean hotelBean)
    {
        List<Hotel> hotels = hotelRepository.queryHotels(hotelBean.getSearchString(),
                hotelBean.getPage() * hotelBean.getPageSize(), hotelBean.getPageSize());
        hotelBean.setHotels(hotels);
    }
}
