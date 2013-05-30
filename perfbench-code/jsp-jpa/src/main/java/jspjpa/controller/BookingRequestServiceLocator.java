/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import jspjpa.service.BookingRepository;
import jspjpa.service.BookingRepositoryBean;
import jspjpa.service.HotelRepository;
import jspjpa.service.HotelRepositoryBean;
import jspjpa.service.UserRepository;
import jspjpa.service.UserRepositoryBean;

/**
 *
 * @author lu4242
 */
public class BookingRequestServiceLocator
{
    public static final Class<?>[] DEFAULT_VALIDATION_GROUPS_ARRAY = new Class<?>[] { Default.class };
    
    private EntityManager em;
    
    private Validator validator;
    
    private ServletContext ctx;
    
    private BookingRepository bookingRepository;
    
    private HotelRepository hotelRepository;
    
    private UserRepository userRepository;
    
    private RequestMessages messages;
    
    public void init(HttpServletRequest req, HttpServletResponse resp, ServletContext ctx)
    {
        this.ctx = ctx;
    }
    
    public void destroy(HttpServletRequest req, HttpServletResponse resp)
    {
        if (em != null)
        {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            em.close();
        }
    }
    
    public EntityManager getEntityManager()
    {
        if (em == null)
        {
            EntityManagerFactory emf =
                (EntityManagerFactory)ctx.getAttribute(BookingListener.ENTITY_MANAGER_FACTORY);
            em = emf.createEntityManager();
        }
        return em;
    }
    
    public Validator getValidator()
    {
        if (validator == null)
        {
            ValidatorFactory factory = 
                    (ValidatorFactory)ctx.getAttribute(BookingListener.VALIDATOR_FACTORY);
            validator = factory.getValidator();
        }
        return validator;
    }
    
    /**
     * @return the bookingRepository
     */
    public BookingRepository getBookingRepository()
    {
        if (bookingRepository == null)
        {
            bookingRepository = new BookingRepositoryBean(getEntityManager());
        }
        return bookingRepository;
    }

    /**
     * @return the hotelRepository
     */
    public HotelRepository getHotelRepository()
    {
        if (hotelRepository == null)
        {
            hotelRepository = new HotelRepositoryBean(getEntityManager());
        }
        return hotelRepository;
    }

    /**
     * @return the userRepository
     */
    public UserRepository getUserRepository()
    {
        if (userRepository == null)
        {
            userRepository = new UserRepositoryBean(getEntityManager());
        }
        return userRepository;
    }

    /**
     * @return the messages
     */
    public RequestMessages getMessages()
    {
        if (messages == null)
        {
            messages = new RequestMessages();
        }
        return messages;
    }

    public void prepareResponseObjects(HttpServletRequest req, HttpServletResponse resp)
    {
        if (messages != null)
        {
            req.setAttribute("messages", messages);
        }
    }
}
