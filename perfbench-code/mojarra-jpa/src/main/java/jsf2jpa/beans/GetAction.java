/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf2jpa.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author lu4242
 */
@ManagedBean(name="getAction")
@RequestScoped
public class GetAction {
    
    private String action;
    
    @ManagedProperty(value="#{bookingSession}")
    private BookingSession bookingSession;

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }
    
    public void doAction()
    {
        if (action != null && "logout".equals(action))
        {
            String target = getBookingSession().logout();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getApplication().getNavigationHandler().handleNavigation(
                    facesContext, null, target+"?faces-redirect=true");
        }
    }

    /**
     * @return the bookingSession
     */
    public BookingSession getBookingSession() {
        return bookingSession;
    }

    /**
     * @param bookingSession the bookingSession to set
     */
    public void setBookingSession(BookingSession bookingSession) {
        this.bookingSession = bookingSession;
    }
}