package grocketjpa.wicket

import javax.persistence.EntityManager
import org.apache.wicket.Page
import org.apache.wicket.RequestCycle
import org.apache.wicket.Response
import org.apache.wicket.protocol.http.PageExpiredException
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.protocol.http.WebRequest
import org.apache.wicket.protocol.http.WebRequestCycle

class JpaRequestCycle extends WebRequestCycle {

    EntityManager em
    boolean endConversation

    static JpaRequestCycle get() {
        return RequestCycle.get()
    }

    JpaRequestCycle(WebApplication application, WebRequest request, Response response) {
        super(application, request, response)
    }

    EntityManager getEntityManager() {
        if (em == null) {
            def emf = application.entityManagerFactory
            em = emf.createEntityManager()
            em.transaction.begin()
        }
        return em
    }
    
    void onEndRequest() {
        super.onEndRequest()
        if (em != null) {
            if (em.transaction.active) {
                em.transaction.commit()
            }
            em.close()
        }
        if (endConversation) {
            request.page.pageMap.remove()
        }
    }
    
    Page onRuntimeException(Page page, RuntimeException e) {
        if (em != null) {
            if (em.transaction.active) {
                em.transaction.rollback()
            }
            em.close()
        }
        if (e instanceof PageExpiredException) {
            session.error "The page you requested has expired."
            return new MainPage()
        }
        return super.onRuntimeException(page, e)
    }

    def endConversation() {
        endConversation = true
    }

}

