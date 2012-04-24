package swicketjpa.wicket

import java.text.NumberFormat
import java.util.Locale
import javax.persistence._
import org.apache.wicket._
import org.apache.wicket.authorization._
import org.apache.wicket.protocol.http._
import org.apache.wicket.protocol.http.request.urlcompressing.UrlCompressingWebRequestProcessor
import org.apache.wicket.util.convert.ConverterLocator
import org.apache.wicket.util.convert.converters.BigDecimalConverter

object BookingApplication {
  def get() = Application.get().asInstanceOf[BookingApplication]
}

class BookingApplication extends WebApplication {

  def getHomePage = classOf[HomePage]

  var emf: EntityManagerFactory = null

  override def init: Unit = {
    super.init()
    emf = Persistence.createEntityManagerFactory("bookingDatabase")
    getSecuritySettings().setAuthorizationStrategy(new IAuthorizationStrategy() {
      override def isActionAuthorized(c: Component, a: Action) = true
      override def isInstantiationAuthorized[Component](clazz: Class[Component]): Boolean = {
        if (classOf[TemplatePage].isAssignableFrom(clazz)) {
          if(BookingSession.get().user == null) {
            throw new RestartResponseException(classOf[HomePage])
          }
        }
        return true
      }
    })
    getMarkupSettings().setCompressWhitespace(true)
    mountBookmarkablePage("/home", classOf[HomePage])
    mountBookmarkablePage("/logout", classOf[LogoutPage])
    mountBookmarkablePage("/register", classOf[RegisterPage])
    mountBookmarkablePage("/settings", classOf[PasswordPage])
  }

  override def newRequestCycle(request: Request, response: Response) = 
    new JpaRequestCycle(this, request.asInstanceOf[WebRequest], response)  

  override def newSession(request: Request, response: Response) = new BookingSession(request)

  override def newRequestCycleProcessor = new UrlCompressingWebRequestProcessor()

  override def newConverterLocator: IConverterLocator = {
    val converterLocator = new ConverterLocator()
    val converter = new BigDecimalConverter() {
      override def getNumberFormat(locale: Locale) = NumberFormat.getCurrencyInstance(Locale.US)
    }
    converterLocator.set(classOf[BigDecimal], converter)
    return converterLocator
  }

  def getEntityManagerFactory = emf
  
}
