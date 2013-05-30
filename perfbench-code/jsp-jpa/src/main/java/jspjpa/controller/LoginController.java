/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.controller;

import java.io.IOException;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import jspjpa.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Leonardo Uribe
 */
/*
@Controller
@Scope("request")
@RequestMapping(value = "/home")
@SessionAttributes(types={BookingSession.class, HotelBean.class, BookingBean.class}, 
                   value={BookingSession.NAME, HotelBean.NAME, BookingBean.NAME})*/
public class LoginController
{
    protected static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    //@RequestMapping(method = RequestMethod.GET)
    public void doGetHome(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator) throws ServletException, IOException
    {
        LoginForm form = new LoginForm();
        req.setAttribute("loginForm", form);
        req.getRequestDispatcher("/jsp/home.jsp")
                .forward(req, resp);        
    }
    
    //@RequestMapping(method = RequestMethod.POST)
    public void doPostLogin(HttpServletRequest req, HttpServletResponse resp,
            BookingRequestServiceLocator serviceLocator) throws ServletException, IOException
    {
        //Step 1. Apply Request values
        LoginForm form = new LoginForm();
        form.setUsername(req.getParameter("username"));
        form.setPassword(req.getParameter("password"));
        
        //Step 2. Conversion/Validation
        Set<ConstraintViolation<LoginForm>> constraints = serviceLocator.getValidator().validate(form, 
                BookingRequestServiceLocator.DEFAULT_VALIDATION_GROUPS_ARRAY);
        if (constraints.size() > 0)
        {
            serviceLocator.getMessages().addMessage(null,new Message(null, "Login Failed!"));
            RequestDispatcher view = req.getRequestDispatcher("/jsp/home.jsp");
            req.setAttribute("loginForm", form);
            serviceLocator.prepareResponseObjects(req, resp);
            view.forward(req, resp);
            return;
        }

        User validUser = serviceLocator.getUserRepository().authenticate(form.getUsername(), form.getPassword());
        if (validUser == null)
        {
            if (BookingListener.LOG_ENABLED)
            {
                logger.error("Login failed");
            }
            serviceLocator.getMessages().addMessage("username",new Message("username", "Login Failed"));
            form.setPassword(null);
            req.setAttribute("loginForm", form);
            RequestDispatcher view = req.getRequestDispatcher("/jsp/home.jsp");
            serviceLocator.prepareResponseObjects(req, resp);
            view.forward(req, resp);
            return;
        }
        
        //Store user into session
        BookingSession bookingSession = new BookingSession();
        bookingSession.setUser(validUser);
        req.getSession().setAttribute(BookingSession.NAME, bookingSession);
       
        if (BookingListener.LOG_ENABLED)
        {
            logger.info("Login succeeded");
        }
        
        //Step 3. Render
        //put hotelBean and BookingBean on session
        RequestDispatcher view = req.getRequestDispatcher("/jsp/main.jsp");
        serviceLocator.getMessages().addMessage(null,new Message(null, "Welcome, " + form.getUsername()));

        HotelBean hotelBean = new HotelBean();
        req.getSession().setAttribute(HotelBean.NAME, hotelBean);
        //req.setAttribute(HotelBean.NAME, hotelBean);
        
        BookingBean bookingBean = new BookingBean();
        bookingBean.setBookings(serviceLocator.getBookingRepository().queryBookings(form.getUsername()));
        req.getSession().setAttribute(BookingBean.NAME, bookingBean);
        //req.setAttribute(BookingBean.NAME, bookingBean);
        
        serviceLocator.prepareResponseObjects(req, resp);
        view.forward(req, resp);
    }

}
