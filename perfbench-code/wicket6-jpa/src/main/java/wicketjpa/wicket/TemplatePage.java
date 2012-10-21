package wicketjpa.wicket;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplatePage extends WebPage {

    protected static final Logger logger = LoggerFactory.getLogger(TemplatePage.class);        

    public TemplatePage() {
        add(new Label("userName", new PropertyModel(this, "session.user.name")));
        add(new BookmarkablePageLink<Void>("search", MainPage.class));
        add(new BookmarkablePageLink<Void>("settings", PasswordPage.class));
        add(new BookmarkablePageLink<Void>("logout", LogoutPage.class));
    }

    protected EntityManager getEntityManager() {
        return JpaRequestCycleListener.getEntityManager();
    }

    protected void endConversation() {
        JpaRequestCycleListener.endConversation();
    }

    protected BookingSession getBookingSession() {
        return (BookingSession) getSession();
    }

    protected void loadBookings() {
        Query query = getEntityManager().createQuery("select b from Booking b"
                + " where b.user.username = :username order by b.checkinDate");
        query.setParameter("username", getBookingSession().getUser().getUsername());
        getBookingSession().setBookings(query.getResultList());
    }    
}
