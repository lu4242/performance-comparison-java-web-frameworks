/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/**
 *
 * @author lu4242
 */
public class BookingListener implements ServletContextListener
{
    public static final boolean LOG_ENABLED = false;
    
    public static final String ENTITY_MANAGER_FACTORY = "emf";
    
    public static final String VALIDATOR_FACTORY = "validatorFactory";

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookingDatabase");    
        sce.getServletContext().setAttribute(ENTITY_MANAGER_FACTORY, emf);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        sce.getServletContext().setAttribute(VALIDATOR_FACTORY, factory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().
                getAttribute(ENTITY_MANAGER_FACTORY);
        if (emf != null && emf.isOpen())
        {
            emf.close();
        }
    }
}
