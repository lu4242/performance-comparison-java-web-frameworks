package wicketjpa.wicket;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebPage;

public class LogoutPage extends WebPage {

    public LogoutPage() {
        getSession().invalidate();
        throw new RestartResponseException(HomePage.class);
    }

}
