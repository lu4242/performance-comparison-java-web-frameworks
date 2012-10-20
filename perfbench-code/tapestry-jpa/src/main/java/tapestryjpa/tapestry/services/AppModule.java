package tapestryjpa.tapestry.services;

import java.text.DecimalFormat;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.tapestry5.beanvalidator.BeanValidatorConfigurer;
import org.apache.tapestry5.beanvalidator.BeanValidatorSource;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Dispatcher;

public class AppModule {

    public static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("$0.00");

    /**
     * Initialize JpaEntityManagerFactoryService with singleton scope (or application scope),
     * so EntityManagerFactory can be shared between requests.
     * 
     * @param emf
     * @return 
     */
    @Scope(ScopeConstants.DEFAULT)
    public static JpaEntityManagerFactoryService buildJpaEntityManagerFactoryService(EntityManagerFactory emf)
    {
        return new JpaEntityManagerFactoryServiceImpl(emf);
    }
    
    /**
     * TODO: JpaServiceImpl does not work well, replaced with 
     * JpaEntityManagerFactoryService and doing EntityManager initialization/shutdown
     * manually. It could be good to use tapestry-jpa, but no idea how to do it.
     * Anyway, do it manually will work well.
     * 
     * @param emf
     * @return 
     */
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
    
    @Contribute(BeanValidatorSource.class)
    public static void provideBeanValidatorConfigurer(OrderedConfiguration<BeanValidatorConfigurer> configuration)
    {
       configuration.add("MyConfigurer", new BeanValidatorConfigurer()
       {
          public void configure(javax.validation.Configuration<?> configuration)
          {
             configuration.ignoreXmlConfiguration();
          }
       });
    }
}
