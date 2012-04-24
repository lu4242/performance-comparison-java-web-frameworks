package grocketjpa.wicket

import java.util.List
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import org.apache.wicket.Request
import org.apache.wicket.Session
import org.apache.wicket.RequestCycle
import org.apache.wicket.protocol.http.WebSession
import grocketjpa.entity.Booking
import grocketjpa.entity.Hotel
import grocketjpa.entity.User

class BookingSession extends WebSession {

    def static final long serialVersionUID = 1L

    User user
    String searchString
    List<Hotel> hotels
    List<Booking> bookings
    int pageSize = 10
    int page

    static BookingSession get() {
        def session = Session.get()
        if (BookingSession.class.classLoader != session.class.classLoader) {
            def os = new ByteArrayOutputStream()
            def oo = new ObjectOutputStream(os)
            oo.writeObject(session)
            oo.close()
            def is = new ByteArrayInputStream(os.toByteArray())
            def oi = new ObjectInputStream(is) {
               Class resolveClass(ObjectStreamClass clazz) {
                    Class.forName(clazz.name, true, BookingSession.class.classLoader)
               }
            }
            def oldSession = oi.readObject()
            def newSession = oldSession.clone()
            Session.set(newSession)
            return newSession
        }
        return session
    }

    BookingSession clone() {
        def session = new BookingSession(RequestCycle.get().request)
        session.user = user
        session.searchString = searchString
        session.hotels = hotels
        session.bookings = bookings
        session.pageSize = 10
        session.page = page
        return session
    }

    BookingSession(Request request) {
        super(request)
    }
	
}

