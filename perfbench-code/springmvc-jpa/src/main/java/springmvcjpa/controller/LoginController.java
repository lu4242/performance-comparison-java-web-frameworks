/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package springmvcjpa.controller;

import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import springmvcjpa.entity.User;
import springmvcjpa.service.BookingRepository;
import springmvcjpa.service.UserRepository;

/**
 *
 * @author Leonardo Uribe
 */
@Controller
@Scope("request")
@RequestMapping(value = "/home")
@SessionAttributes(types={BookingSession.class, HotelBean.class, BookingBean.class}, 
                   value={BookingSession.NAME, HotelBean.NAME, BookingBean.NAME})
public class LoginController
{
    protected static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @RequestMapping(method = RequestMethod.GET)
	public String home(Map model)
	{
        model.put("loginForm", new LoginForm());
		return "home";
	}

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView login(
            @Valid LoginForm user, 
            BindingResult bindingResult,
            ModelAndView modelAndView)
    {
        if (bindingResult.hasErrors())
        {
            user.setPassword(null);
            modelAndView.getModelMap().put("loginForm",user);
            return new ModelAndView("home") ;
        }
        
        User validUser = userRepository.authenticate(user.getUsername(), user.getPassword());
        if (validUser == null)
        {
            
            if (BookingApplication.LOG_ENABLED)
            {
                logger.error("Login failed");
            }
            bindingResult.addError(new ObjectError("username", "Login Failed"));
            LoginForm loginForm = new LoginForm();
            loginForm.setUsername(user.getUsername());
            modelAndView.getModelMap().put("loginForm", loginForm);
            modelAndView.setViewName("home");
            return modelAndView;
        }
        //Store user into sessionç
        BookingSession bookingSession = new BookingSession();
        bookingSession.setUser(validUser);
        modelAndView.getModelMap().put(BookingSession.NAME, bookingSession);
        
        if (BookingApplication.LOG_ENABLED)
        {
            logger.info("Login succeeded");
        }
        //result.addError(new ObjectError(null, "Welcome, " + user.getUsername()));
        modelAndView.getModelMap().put("infoMessage", "Welcome, " + user.getUsername());

        modelAndView.setViewName("main");
        modelAndView.addObject("infoMessage", "Welcome, " + user.getUsername());
        HotelBean hotelBean = new HotelBean();

        modelAndView.getModelMap().addAttribute(HotelBean.NAME, hotelBean);
        BookingBean bookingBean = new BookingBean();
        bookingBean.setBookings(bookingRepository.queryBookings(user.getUsername()));

        modelAndView.getModelMap().addAttribute(BookingBean.NAME, bookingBean);
        return modelAndView;
    }
    
}
