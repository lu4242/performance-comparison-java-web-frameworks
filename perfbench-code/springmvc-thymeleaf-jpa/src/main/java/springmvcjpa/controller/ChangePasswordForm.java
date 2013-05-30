/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package springmvcjpa.controller;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author lu4242
 */
public class ChangePasswordForm
{
    private String password;
    
    private String verify;

    /**
     * @return the password
     */
    @NotNull
    @Size(min = 5, max = 15)
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

    /**
     * @return the verify
     */
    @NotNull
    @Size(min = 5, max = 15)
    public String getVerify()
    {
        return verify;
    }

    /**
     * @param verify the verify to set
     */
    public void setVerify(String verify)
    {
        this.verify = verify;
    }
    
}
