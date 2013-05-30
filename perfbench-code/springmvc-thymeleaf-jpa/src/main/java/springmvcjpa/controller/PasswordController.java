/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package springmvcjpa.controller;

import java.util.Map;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Leonardo Uribe
 */
@Controller
@SessionAttributes(types=HotelBean.class, 
                   value=HotelBean.NAME)
@RequestMapping(value="/password")
public class PasswordController
{
    
    @RequestMapping(method = RequestMethod.GET)
	public String show(Map model)
	{
        model.put("changePasswordForm", new ChangePasswordForm());
		return "password";
	}
    
    @RequestMapping(method=RequestMethod.POST, params="change")
    public ModelAndView change(
            @Valid ChangePasswordForm form,
            BindingResult bindingResult,
            ModelAndView modelAndView)
    {
        //TODO: Implement me!
        
        modelAndView.setViewName("main");
        return modelAndView;
    }    
    
    @RequestMapping(method=RequestMethod.POST, params="cancel")
    public String cancel()
    {
        return "main";
    }
}
