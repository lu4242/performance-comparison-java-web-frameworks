package grocketjpa.wicket

import org.apache.wicket.RestartResponseException
import org.apache.wicket.markup.html.WebPage

class LogoutPage extends WebPage {

    LogoutPage() {
        session.invalidate()
        throw new RestartResponseException(HomePage.class)
    }

}


