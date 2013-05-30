/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package springmvcjpa.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author lu4242
 */
@Controller
@SessionAttributes(types={BookingSession.class, HotelBean.class, BookingBean.class}, 
                   value={BookingSession.NAME, HotelBean.NAME, BookingBean.NAME})
@RequestMapping(value="/book")
public class BookController
{
    
	@InitBinder("bookForm")
	public void initBinder(WebDataBinder binder)
    {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
 		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
    
    @RequestMapping(method=RequestMethod.POST, params="proceed")
    public ModelAndView proceed(@Valid BookForm bookForm,
                                BindingResult bindingResult,
                                @ModelAttribute(BookingBean.NAME) BookingBean bookingBean,
                                ModelAndView modelAndView)
    {
        if (bindingResult.hasErrors())
        {
            return modelAndView;
        }
        bookForm.merge(bookingBean.getBooking());
        modelAndView.setViewName("confirm");
        return modelAndView;
    }
    
    @RequestMapping(method=RequestMethod.POST, params="cancel")
    public String cancel()
    {
        return "main";
    }
    
    @RequestMapping(method=RequestMethod.POST, headers="X-Requested-With=XMLHttpRequest")
	public ModelAndView validateAjax(
            @RequestParam("val") String command,
            @Valid BookForm bookForm,
            BindingResult bindingResult,
            ModelAndView modelAndView)
	{
        if (bindingResult.hasErrors())
        {
            //In Ajax case, form:errors does not work as expected, so we have to inject the message for ourselves
            FieldError fieldError = bindingResult.getFieldError(command);
            if (fieldError != null)
            {
                modelAndView.addObject("errorMsg", fieldError.getDefaultMessage());
            }
        }
        modelAndView.setViewName("ajax/book_"+command);
        return modelAndView;
	}    
}
