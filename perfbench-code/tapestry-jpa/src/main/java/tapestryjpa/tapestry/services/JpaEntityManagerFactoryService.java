/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tapestryjpa.tapestry.services;

import javax.persistence.EntityManager;

/**
 * 
 * @author Leonardo Uribe
 */
public interface JpaEntityManagerFactoryService
{
    public EntityManager createEntityManager();
}
