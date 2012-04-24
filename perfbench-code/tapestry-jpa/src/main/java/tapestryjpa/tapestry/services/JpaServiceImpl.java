package tapestryjpa.tapestry.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.apache.tapestry5.ioc.services.ThreadCleanupListener;

public class JpaServiceImpl implements JpaService, ThreadCleanupListener {

    private EntityManagerFactory emf;
    private EntityManager em;

    public JpaServiceImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        if (em == null) {
            em = emf.createEntityManager();
            em.getTransaction().begin();
        }
        return em;
        
    }

    public void threadDidCleanup() {
        if (em != null) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            em.close();
        }
    }

}
