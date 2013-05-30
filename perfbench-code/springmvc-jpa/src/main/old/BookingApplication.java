package springmvcjpa.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
@Scope("singleton")
public class BookingApplication
{
    public static final boolean LOG_ENABLED = false;
    
    private EntityManagerFactory emf;
    
    @PostConstruct
    public void init()
    {
        emf = Persistence.createEntityManagerFactory("bookingDatabase");    
    }
    
    @PreDestroy
    public void destroy()
    {
        if (emf != null && emf.isOpen())
        {
            emf.close();
        }
    }
 
    public EntityManagerFactory getEntityManagerFactory()
    {
        return emf;
    }
}
