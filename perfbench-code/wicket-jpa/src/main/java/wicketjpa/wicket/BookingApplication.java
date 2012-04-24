package wicketjpa.wicket;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.wicket.Component;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.request.urlcompressing.UrlCompressingWebRequestProcessor;
import org.apache.wicket.request.IRequestCycleProcessor;
import org.apache.wicket.util.convert.ConverterLocator;
import org.apache.wicket.util.convert.converters.BigDecimalConverter;

public class BookingApplication extends WebApplication {

    private EntityManagerFactory emf;

    @Override
    public Class getHomePage() {
        return MainPage.class;
    }

    @Override
    public void init() {
        super.init();
        emf = Persistence.createEntityManagerFactory("bookingDatabase");
        getSecuritySettings().setAuthorizationStrategy(new IAuthorizationStrategy() {
            @Override
            public boolean isActionAuthorized(Component c, Action a) {
                return true;
            }
            @Override
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
        mountBookmarkablePage("/home", HomePage.class);
        mountBookmarkablePage("/logout", LogoutPage.class);
        mountBookmarkablePage("/register", RegisterPage.class);
        mountBookmarkablePage("/settings", PasswordPage.class);        
    }

    @Override
    public RequestCycle newRequestCycle(Request request, Response response) {
        return new JpaRequestCycle(this, (WebRequest) request, response);
    }

    @Override
    public BookingSession newSession(Request request, Response response) {
        return new BookingSession(request);
    }

    @Override
    protected IRequestCycleProcessor newRequestCycleProcessor() {
        return new UrlCompressingWebRequestProcessor();
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

    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
