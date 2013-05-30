/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.controller;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Leonardo Uribe
 */
public class LoginForm
{
    @NotNull
    @Size(max = 100)
    private String username;
    
    @NotNull
    @Size(min = 4, max = 15)
    private String password;

    public LoginForm()
    {
    }
    
    public LoginForm(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
    
    /**
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
    
}
