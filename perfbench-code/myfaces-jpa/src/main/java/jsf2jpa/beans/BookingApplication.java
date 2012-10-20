package jsf2jpa.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean(name="bookingApplication", eager=true)
@ApplicationScoped
public class BookingApplication
{
    
    public static final boolean LOG_ENABLED = false;
    
    private EntityManagerFactory emf;
    
    private SelectItem[] months;
    
    private SelectItem[] years;
    
    @PostConstruct
    public void init()
    {
        emf = Persistence.createEntityManagerFactory("bookingDatabase");    
        getMonths();
        getYears();
    }
    
    public SelectItem[] getMonths()
    {
        if (months == null)
        {
            months = new SelectItem[12];
            months[0] = new SelectItem(1,"Jan");
            months[1] = new SelectItem(2,"Feb");
            months[2] = new SelectItem(3,"Mar");
            months[3] = new SelectItem(4,"Apr");
            months[4] = new SelectItem(5,"May");
            months[5] = new SelectItem(6,"Jun");
            months[6] = new SelectItem(7,"Jul");
            months[7] = new SelectItem(8,"Aug");
            months[8] = new SelectItem(9,"Sep");
            months[9] = new SelectItem(10,"Oct");
            months[10] = new SelectItem(11,"Nov");
            months[11] = new SelectItem(12,"Dec");
        }
        return months;
    }
    
    public SelectItem[] getYears()
    {
        if (years == null)
        {
            years = new SelectItem[5];
            years[0] = new SelectItem(2006, "2006");
            years[1] = new SelectItem(2007, "2007");
            years[2] = new SelectItem(2008, "2008");
            years[3] = new SelectItem(2009, "2009");
            years[4] = new SelectItem(2010, "2010");
        }
        return years;
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
