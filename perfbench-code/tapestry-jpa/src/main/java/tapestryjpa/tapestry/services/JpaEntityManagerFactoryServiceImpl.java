/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tapestryjpa.tapestry.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo Uribe
 */
public class JpaEntityManagerFactoryServiceImpl implements JpaEntityManagerFactoryService
{
    private EntityManagerFactory emf;

    public JpaEntityManagerFactoryServiceImpl(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    @Override
    public EntityManager createEntityManager()
    {
        return emf.createEntityManager();
    }
    
}
