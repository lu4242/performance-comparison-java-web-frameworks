package grocketjpa.wicket

import javax.persistence.EntityManager
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.model.PropertyModel
import org.slf4j.LoggerFactory

class TemplatePage extends WebPage {

    static final logger = LoggerFactory.getLogger(TemplatePage.class)

    TemplatePage() {
        add new Label("userName", new PropertyModel(this, "session.user.name"))
        add new BookmarkablePageLink("search", MainPage.class)
        add new BookmarkablePageLink("settings", PasswordPage.class)
        add new BookmarkablePageLink("logout", LogoutPage.class)
    }

    EntityManager getEntityManager() {
        return requestCycle.entityManager
    }

    void endConversation() {
        requestCycle.endConversation()
    }

    void loadBookings() {
        def query = entityManager.createQuery("select b from Booking b"
                + " where b.user.username = :username order by b.checkinDate")
        query.setParameter("username", session.user.username)
        session.bookings = query.resultList
    } 
	
}

