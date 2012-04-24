package tapestryjpa.tapestry.services;

import java.text.DecimalFormat;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Dispatcher;

public class AppModule {

    public static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("$0.00");

    @Scope(ScopeConstants.PERTHREAD)
    public static JpaService buildJpaService(EntityManagerFactory emf) {
        return new JpaServiceImpl(emf);
    }

    @EagerLoad
    public static EntityManagerFactory buildEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("bookingDatabase");
    }
    
    public static AuthDispatcher buildAuthDispatcher(ApplicationStateManager asm) {
        return new AuthDispatcher(asm);
    }

    public void contributeMasterDispatcher(OrderedConfiguration<Dispatcher> configuration, AuthDispatcher authDispatcher) {
        configuration.add("AuthDispatcher", authDispatcher, "before:PageRender");
    }
    
}
