/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.controller;

/**
 *
 * @author lu4242
 */
public class Message
{
    private String name;

    private String value;

    public Message(String name, String value)
    {
        this.name = name;
        this.value = value;
    }
    
    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value)
    {
        this.value = value;
    }
}
