/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf2jpa.beans;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import jsf2jpa.entity.User;

@ManagedBean(name="bookingSession")
@SessionScoped
public class BookingSession implements Serializable
{
    public static final String NAME = "bookingSession";
    
    private User user;
 
    
    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void info(String message)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        message, message ));
    }
    
    public String logout()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().invalidateSession();
        return "home";
    }
}
