package swicketjpa.wicket

import org.apache.wicket._
import org.apache.wicket.protocol.http.WebSession
import swicketjpa.entity._

object BookingSession {
  def get() = Session.get().asInstanceOf[BookingSession]
}

class BookingSession(request: Request) extends WebSession(request) {

  var user: User = null
  var searchString: String = null
  var hotels: java.util.List[Hotel] = null
  var bookings: java.util.List[Booking] = null
  var pageSize: Int = 10
  var page: Int = 0

}
