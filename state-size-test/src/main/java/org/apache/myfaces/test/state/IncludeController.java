/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.myfaces.test.state;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author lu4242
 */
@ManagedBean(name = "includeController")
@SessionScoped
public class IncludeController
{
    private String include;
    
    @PostConstruct
    public void init()
    {
        includeA();
    }

    public String getInclude()
    {
        return include;
    }
    
    public void includeA()
    {
        include = "/includes/includeA.xhtml";
    }

    public void includeB()
    {
        include = "/includes/includeB.xhtml";
    }

    public void includeC()
    {
        include = "/includes/includeC.xhtml";
    }
}
