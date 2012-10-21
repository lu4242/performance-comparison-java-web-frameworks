package wicketjpa.wicket;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.PageExpiredException;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.core.request.handler.IPageProvider;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;

public class JpaRequestCycleListener extends AbstractRequestCycleListener
{
	private static final MetaDataKey<EntityManager> ENTITY_MANAGER_META_DATA_KEY = new MetaDataKey<EntityManager>()
	{
	};

	private static final MetaDataKey<Boolean> END_CONVERSATION_META_DATA_KEY = new MetaDataKey<Boolean>()
	{
	};
	
    public static EntityManager getEntityManager() {
	EntityManager em = RequestCycle.get().getMetaData(ENTITY_MANAGER_META_DATA_KEY);
        if (em == null) {
	    //EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookingDatabase");
            EntityManagerFactory emf = ((BookingApplication)BookingApplication.get()).getEntityManagerFactory();
            em = emf.createEntityManager();
            em.getTransaction().begin();
			RequestCycle.get().setMetaData(ENTITY_MANAGER_META_DATA_KEY, em);
        }
        return em;
    }

	@Override
	public void onBeginRequest(RequestCycle cycle)
	{
		super.onBeginRequest(cycle);
		cycle.setMetaData(END_CONVERSATION_META_DATA_KEY, Boolean.FALSE);
	}

	@Override
    public void onEndRequest(RequestCycle cycle) {
        super.onEndRequest(cycle);
        EntityManager em = getEntityManager();
        if (em != null) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            if (em.isOpen())
            {
                em.close();
            }
        }
        if (cycle.getMetaData(END_CONVERSATION_META_DATA_KEY)) {
	        // this is not implemented in Wicket 1.5
//            cycle.getRequest().getPage().getPageMap().remove();
        }
    }

    @Override
    public IRequestHandler onException(RequestCycle cycle, Exception e) {
	    EntityManager em = getEntityManager();
	    if (em != null) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (em.isOpen())
            {
                em.close();
            }
        }
        if (e instanceof PageExpiredException) {
            Session.get().error("The page you requested has expired.");
	        IPageProvider pageProvider = new PageProvider(new MainPage());
            return new RenderPageRequestHandler(pageProvider);
        }
        return super.onException(cycle, e);
    }

    public static void endConversation() {
	    RequestCycle.get().setMetaData(END_CONVERSATION_META_DATA_KEY, Boolean.TRUE);
    }

}
