package wicketjpa.wicket;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.wicket.*;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.page.DefaultPageManagerContext;
import org.apache.wicket.page.IPageManagerContext;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.pageStore.memory.HttpSessionDataStore;
import org.apache.wicket.pageStore.memory.PageNumberEvictionStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.convert.converter.BigDecimalConverter;

public class BookingApplication extends WebApplication {

    public static final boolean LOG_ENABLED = false;
    
    @Override
    public Class<? extends Page> getHomePage() {
        return MainPage.class;
    }

    private EntityManagerFactory emf;

    @Override
    public void init() {
        super.init();

        emf = Persistence.createEntityManagerFactory("bookingDatabase");
        
        getSecuritySettings().setAuthorizationStrategy(new IAuthorizationStrategy() {

	        public boolean isActionAuthorized(Component c, Action a) {
                return true;
            }

	        public boolean isInstantiationAuthorized(Class clazz) {
                if (TemplatePage.class.isAssignableFrom(clazz)) {
                    if (BookingSession.get().getUser() == null) {
                        throw new RestartResponseException(HomePage.class);
                    }
                }
                return true;
            }
        });
        getMarkupSettings().setCompressWhitespace(true);
        mountPage("/home", HomePage.class);
        mountPage("/logout", LogoutPage.class);
        mountPage("/register", RegisterPage.class);
        mountPage("/settings", PasswordPage.class);

	getRequestCycleListeners().add(new JpaRequestCycleListener());
        
        // Store pages into session. Test shows a big detriment in performance.
        // when using it. Too bad.
        /*
        setPageManagerProvider(new DefaultPageManagerProvider(this)
        {
            protected IDataStore newDataStore() 
            { 
                return  new HttpSessionDataStore(pageManagerContext, new PageNumberEvictionStrategy(10));
            }
        });
        */
    }

    /*
    private final IPageManagerContext pageManagerContext = new DefaultPageManagerContext();

    @Override
    protected IPageManagerContext getPageManagerContext() {
        return pageManagerContext;
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (emf != null && emf.isOpen())
        {
            emf.close();
        }
    }

    @Override
    public BookingSession newSession(Request request, Response response) {
        return new BookingSession(request);
    }

//    @Override
//    protected ISessionStore newSessionStore() {
//        return new HttpSessionStore(this);
//    }

    @Override
    protected IConverterLocator newConverterLocator() {
        ConverterLocator converterLocator = new ConverterLocator();
        BigDecimalConverter converter = new BigDecimalConverter() {
            @Override
            public NumberFormat getNumberFormat(Locale locale) {
                return DecimalFormat.getCurrencyInstance(Locale.US);
            }
        };
        converterLocator.set(BigDecimal.class, converter);
        return converterLocator;
    }

    public EntityManagerFactory getEntityManagerFactory()
    {
        return emf;
    }
}
