package tapestryjpa.tapestry.services;

import javax.persistence.EntityManager;

public interface JpaService {

    public EntityManager getEntityManager();

}
