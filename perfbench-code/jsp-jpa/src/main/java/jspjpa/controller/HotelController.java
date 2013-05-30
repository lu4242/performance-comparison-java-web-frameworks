/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.controller;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jspjpa.entity.Booking;
import jspjpa.entity.Hotel;
import jspjpa.service.HotelRepository;

/**
 *
 * @author lu4242
 */
/*
@Controller
@SessionAttributes(types={BookingSession.class, HotelBean.class, BookingBean.class}, 
                   value={BookingSession.NAME, HotelBean.NAME, BookingBean.NAME})*/
public class HotelController
{

    //@RequestMapping(value="/hotel", method=RequestMethod.GET)
	public void doGetHotel(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator) throws ServletException, IOException
	{
        Long id = Long.valueOf(req.getParameter("id"));
        HotelRepository hotelRepository = serviceLocator.getHotelRepository();
        BookingSession bookingSession = (BookingSession) req.getSession().getAttribute(BookingSession.NAME);
        Hotel hotel = hotelRepository.queryFindById(id);
        Booking booking = new Booking(hotel, bookingSession.getUser());
        Calendar calendar = Calendar.getInstance();
        booking.setCheckinDate(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        booking.setCheckoutDate(calendar.getTime());
        BookingBean bookingBean = (BookingBean) req.getSession().getAttribute(BookingBean.NAME);
        bookingBean.setBooking(booking);
        req.setAttribute("hotel", hotel);
        req.getRequestDispatcher("/jsp/hotel.jsp")
                .forward(req, resp);
	}
    
    //@RequestMapping(value="/hotel", method=RequestMethod.POST, params="book")
    public void doPostBook(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator) throws ServletException, IOException
    {
        BookingBean bookingBean = (BookingBean) req.getSession().getAttribute(BookingBean.NAME);
        req.setAttribute("bookForm", new BookForm(bookingBean.getBooking()));
        req.getRequestDispatcher("/jsp/book.jsp")
                .forward(req, resp);
    }
    
    //@RequestMapping(value="/hotel", method=RequestMethod.POST, params="cancel")
    public void doPostCancel(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator) throws ServletException, IOException
    {
        req.getRequestDispatcher("/jsp/main.jsp")
                .forward(req, resp);
    }

}
