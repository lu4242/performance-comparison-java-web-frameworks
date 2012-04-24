package jsf2jpa.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean(name="bookingApplication", eager=true)
@ApplicationScoped
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
