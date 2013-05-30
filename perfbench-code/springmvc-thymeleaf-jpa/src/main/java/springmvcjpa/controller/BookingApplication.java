/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package springmvcjpa.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author lu4242
 */
@Component
@Lazy(false)
@Scope("singleton")
public class BookingApplication
{
    public static final boolean LOG_ENABLED = false;
    
    @PostConstruct
    public void init()
    {
    }
    
    @PreDestroy
    public void destroy()
    {
    }
}
