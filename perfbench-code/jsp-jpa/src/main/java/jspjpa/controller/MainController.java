/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jspjpa.entity.Booking;
import jspjpa.entity.Hotel;
import jspjpa.service.BookingRepository;
import jspjpa.service.HotelRepository;

/**
 *
 * @author Leonardo Uribe
 */
/*
@Controller
@SessionAttributes(types={BookingSession.class, HotelBean.class, BookingBean.class}, 
                   value={BookingSession.NAME, HotelBean.NAME, BookingBean.NAME})*/
public class MainController
{
    
    public void doGetMain(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator)
             throws ServletException, IOException
    {
        req.getRequestDispatcher("/jsp/main.jsp")
                .forward(req, resp); 
    }
    
    //@RequestMapping(value="/logout", method=RequestMethod.GET)
    public void doGetLogout(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException
    {
        HttpSession session = request.getSession(false);
        if (session != null)
        {
            session.invalidate();
        }
        response.sendRedirect("home.do");
    }

    //@RequestMapping(value="/main", method=RequestMethod.POST)
	public void doPostSearch(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator) throws ServletException, IOException
	{
        HotelBean hotelBean = (HotelBean) req.getSession().getAttribute(HotelBean.NAME);
        hotelBean.setPageSize(Integer.valueOf(req.getParameter("pageSize")));
        hotelBean.setSearchString(req.getParameter("searchString"));
        
        hotelBean.setPage(0);
        queryHotels(serviceLocator.getHotelRepository(), hotelBean);
        
        req.getRequestDispatcher("/jsp/main.jsp")
                .forward(req, resp); 
	}
    
    
    //@RequestMapping(value="/main", method=RequestMethod.POST, headers="X-Requested-With=XMLHttpRequest")
	public void doPostSearchAjax(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator) throws ServletException, IOException
	{
        HotelBean hotelBean = (HotelBean) req.getSession().getAttribute(HotelBean.NAME);
        hotelBean.setPageSize(Integer.valueOf(req.getParameter("pageSize")));
        hotelBean.setSearchString(req.getParameter("searchString"));
        
        hotelBean.setPage(0);
        queryHotels(serviceLocator.getHotelRepository(), hotelBean);
        
        req.getRequestDispatcher("/jsp/hotelstable.jsp")
                .forward(req, resp); 
	}
    
    //@RequestMapping(value="/main", method=RequestMethod.POST, params="cancelId")
	public void doPostCancel(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator) throws ServletException, IOException
	{
        Long cancelId = Long.valueOf(req.getParameter("cancelId"));
        BookingRepository bookingRepository = serviceLocator.getBookingRepository();
        Booking cancelled = bookingRepository.find(cancelId);
        BookingBean bookingBean = (BookingBean) req.getSession().getAttribute(BookingBean.NAME);
        BookingSession bookingSession = (BookingSession) req.getSession().getAttribute(BookingSession.NAME);
        if (cancelled != null)
        {
            bookingRepository.cancel(cancelId);
            bookingBean.setBookings(bookingRepository.queryBookings(bookingSession.getUser().getUsername()));
            serviceLocator.getMessages().addMessage(null, 
                    new Message(null, "Booking cancelled for confirmation number "+cancelled.getId()));
        }
        serviceLocator.prepareResponseObjects(req, resp);
        req.getRequestDispatcher("/jsp/main.jsp")
                .forward(req, resp);
	}
    
    //@RequestMapping(value="/main", method=RequestMethod.GET, params="action")
	public void doGetNextPage(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator) throws ServletException, IOException
    {
        String action = req.getParameter("action");
        HotelBean hotelBean = (HotelBean) req.getSession().getAttribute(HotelBean.NAME);
        if ("nextPage".equals(action))
        {
            nextPage(serviceLocator.getHotelRepository(), hotelBean);
        }
        req.getRequestDispatcher("/jsp/main.jsp")
                .forward(req, resp);
    }
    
    public void nextPage(HotelRepository hotelRepository, HotelBean hotelBean) {
        hotelBean.setPage(hotelBean.getPage()+1);
        queryHotels(hotelRepository, hotelBean);
    }
    
    public boolean isNextPageAvailable(HotelBean hotelBean)
    {
        return hotelBean.isNextPageAvailable();
    }

    private void queryHotels(HotelRepository hotelRepository, HotelBean hotelBean)
    {
        List<Hotel> hotels = hotelRepository.queryHotels(hotelBean.getSearchString(),
                hotelBean.getPage() * hotelBean.getPageSize(), hotelBean.getPageSize());
        hotelBean.setHotels(hotels);
    }
}
