/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.service;

import jspjpa.entity.User;

/**
 *
 * @author lu4242
 */
public interface UserRepository
{
    
    public User authenticate(String username, String password);
    
}
