/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;

/**
 *
 * @author lu4242
 */
/*
@Controller
@SessionAttributes(types={BookingSession.class, HotelBean.class, BookingBean.class}, 
                   value={BookingSession.NAME, HotelBean.NAME, BookingBean.NAME})
@RequestMapping(value="/book")*/
public class BookController
{
    /*
	@InitBinder("bookForm")
	public void initBinder(WebDataBinder binder)
    {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
 		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}*/
    
    //@RequestMapping(method=RequestMethod.POST, params="proceed")
    public void doPostProceed(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator) throws ServletException, IOException
    {
        BookingBean bookingBean = (BookingBean) req.getSession().getAttribute(BookingBean.NAME);
        BookForm bookForm = new BookForm(bookingBean.getBooking());
        
        //Step 1. Apply Request values
        decode(req, bookForm);
        
        Set<ConstraintViolation<BookForm>> constraints = serviceLocator.getValidator().validate(bookForm, 
                BookingRequestServiceLocator.DEFAULT_VALIDATION_GROUPS_ARRAY);
        if (constraints.size() > 0)
        {
            //TODO:
            for (ConstraintViolation<BookForm> constraint : constraints)
            {
                String path = constraint.getPropertyPath().toString();
                String message = constraint.getMessage();
                serviceLocator.getMessages().addMessage(path, new Message(path, message) );
            }

            RequestDispatcher view = req.getRequestDispatcher("/jsp/book.jsp");
            req.setAttribute("bookForm", bookForm);
            serviceLocator.prepareResponseObjects(req, resp);
            view.forward(req, resp);
            return;
        }
        
        bookForm.merge(bookingBean.getBooking());
        req.getRequestDispatcher("/jsp/confirm.jsp")
                .forward(req, resp);
    }
    
    public void decode(HttpServletRequest req, BookForm bookForm)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        if (req.getParameterMap().containsKey("beds"))
        {
            bookForm.setBeds(Integer.valueOf(req.getParameter("beds")));
        }
        if (req.getParameterMap().containsKey("checkinDate"))
        {
            try
            {
                bookForm.setCheckinDate(dateFormat.parse(req.getParameter("checkinDate")));
            }
            catch (ParseException ex)
            {
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (req.getParameterMap().containsKey("checkoutDate"))
        {
            try
            {
                bookForm.setCheckoutDate(dateFormat.parse(req.getParameter("checkoutDate")));
            }
            catch (ParseException ex)
            {
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (req.getParameterMap().containsKey("creditCard"))
        {
            bookForm.setCreditCard(req.getParameter("creditCard"));
        }
        if (req.getParameterMap().containsKey("creditCardExpiryMonth"))
        {
            bookForm.setCreditCardExpiryMonth(Integer.valueOf(req.getParameter("creditCardExpiryMonth")));
        }
        if (req.getParameterMap().containsKey("creditCardExpiryYear"))
        {
            bookForm.setCreditCardExpiryYear(Integer.valueOf(req.getParameter("creditCardExpiryYear")));
        }
        if (req.getParameterMap().containsKey("creditCardName"))
        {
            bookForm.setCreditCardName(req.getParameter("creditCardName"));
        }
        if (req.getParameterMap().containsKey("smoking"))
        {
            bookForm.setSmoking(Boolean.valueOf(req.getParameter("smoking")));
        }
    }
    
    //@RequestMapping(method=RequestMethod.POST, params="cancel")
    public void doPostCancel(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator) throws ServletException, IOException
    {
        req.getRequestDispatcher("/jsp/main.jsp")
                .forward(req, resp);
    }
    
    //@RequestMapping(method=RequestMethod.POST, headers="X-Requested-With=XMLHttpRequest")
	public void doPostValidateAjax(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator) throws ServletException, IOException
	{
        BookingBean bookingBean = (BookingBean) req.getSession().getAttribute(BookingBean.NAME);
        BookForm bookForm = new BookForm(bookingBean.getBooking());
        
        //Step 1. Apply Request values
        decode(req, bookForm);
        
        String command = req.getParameter("val");
        
        Set<ConstraintViolation<BookForm>> constraints = serviceLocator.getValidator().validate(bookForm, 
                BookingRequestServiceLocator.DEFAULT_VALIDATION_GROUPS_ARRAY);
        if (constraints.size() > 0)
        {
            for (ConstraintViolation<BookForm> constraint : constraints)
            {
                String path = constraint.getPropertyPath().toString();
                if (command.equals(path))
                {
                    String message = constraint.getMessage();
                    serviceLocator.getMessages().addMessage(path, new Message(path, message) );
                }
            }
        }

        RequestDispatcher view = req.getRequestDispatcher("/jsp/ajax/book_"+command+".jsp");
        req.setAttribute("bookForm", bookForm);
        serviceLocator.prepareResponseObjects(req, resp);
        view.forward(req, resp);
	}
}
