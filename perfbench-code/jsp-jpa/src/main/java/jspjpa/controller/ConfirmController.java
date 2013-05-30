/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jspjpa.entity.Booking;
import jspjpa.service.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author lu4242
 */
/*
@Controller
@SessionAttributes(types={BookingSession.class, HotelBean.class, BookingBean.class}, 
                   value={BookingSession.NAME, HotelBean.NAME, BookingBean.NAME})
@RequestMapping(value="/confirm")*/
public class ConfirmController
{
    protected static final Logger logger = LoggerFactory.getLogger(ConfirmController.class);
    
    /*
    @Autowired
    private BookingRepository bookingRepository;
    */
    
    //@RequestMapping(method=RequestMethod.POST, params="confirm")
    public void doPostConfirm(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator) throws ServletException, IOException
    {
        BookingRepository bookingRepository = serviceLocator.getBookingRepository();
        BookingBean bookingBean = (BookingBean) req.getSession().getAttribute(BookingBean.NAME);
        BookingSession bookingSession = (BookingSession) req.getSession().getAttribute(BookingSession.NAME);
        Booking booking = bookingBean.getBooking();
        bookingRepository.create(booking);
        
        RequestDispatcher view = req.getRequestDispatcher("/jsp/main.jsp");
        serviceLocator.getMessages().addMessage(null,new Message(null,"Thank you, "+booking.getUser().getName()+
                    ", your confimation number for "+booking.getHotel().getName()+" is "+ booking.getId()));
        
        bookingBean.setBookings(bookingRepository.queryBookings(bookingSession.getUser().getUsername()));
        if (BookingListener.LOG_ENABLED)
        {
            logger.info("New booking: "+booking.getId()+" for "+booking.getUser().getUsername());
        }
        serviceLocator.prepareResponseObjects(req, resp);
        view.forward(req, resp);
    }    
    
    //@RequestMapping(method=RequestMethod.POST, params="revise")
    public void doPostRevise(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator) throws ServletException, IOException
    {
        BookingBean bookingBean = (BookingBean) req.getSession().getAttribute(BookingBean.NAME);
        BookForm bookForm = new BookForm(bookingBean.getBooking());
        req.setAttribute("bookForm", bookForm);
        req.getRequestDispatcher("/jsp/book.jsp")
                .forward(req, resp);
    }
    
    //@RequestMapping(method=RequestMethod.POST, params="cancel")
    public void doPostCancel(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator) throws ServletException, IOException
    {
        req.getRequestDispatcher("/jsp/main.jsp")
                .forward(req, resp);
    }
}
