package swicketjpa.wicket

import javax.persistence.EntityManager
import org.apache.wicket.Page
import org.apache.wicket.RequestCycle
import org.apache.wicket.Response
import org.apache.wicket.protocol.http.PageExpiredException
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.protocol.http.WebRequest
import org.apache.wicket.protocol.http.WebRequestCycle

object JpaRequestCycle {
  def get() = RequestCycle.get().asInstanceOf[JpaRequestCycle]
}

class JpaRequestCycle(application: WebApplication, request: WebRequest, response: Response)
  extends WebRequestCycle(application, request, response) {

  var em: EntityManager = null
  var _endConversation = false

  def getEntityManager(): EntityManager = {
    if(em == null) {
      val emf = getApplication().asInstanceOf[BookingApplication].emf
      em = emf.createEntityManager()
      em.getTransaction().begin()
    }
    return em
  }

  override def onEndRequest = {
    super.onEndRequest()
    if (em != null) {
      if (em.getTransaction().isActive()) em.getTransaction().commit()
      em.close()
    }
    if (_endConversation) getRequest().getPage().getPageMap().remove()    
  }

  override def onRuntimeException(page: Page, e: RuntimeException): Page = {
    if (em != null) {
      if (em.getTransaction().isActive()) em.getTransaction().rollback()
      em.close()
    }
    if (e.isInstanceOf[PageExpiredException]) {
      getSession().error("The page you requested has expired.")
      return new MainPage()
    }
    return super.onRuntimeException(page, e)
  }

  def endConversation() = _endConversation = true  

}
