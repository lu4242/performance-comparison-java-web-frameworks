/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lu4242
 */
public class RequestMessages
{
    private Map<String, List<Message>> _messages = null;
    private List<Message> _orderedMessages = null;
    private Map<String, String> _messagesMap;
    
    public final void addMessage(final String name, final Message message)
    {
        if (message == null)
        {
            throw new NullPointerException("message");
        }

        if (_messages == null)
        {
            _messages = new LinkedHashMap<String, List<Message>>();
            _orderedMessages = new ArrayList<Message>();
        }
        
        List<Message> lst = _messages.get(name); 
        if (lst == null)
        {
            lst = new ArrayList<Message>();
            _messages.put(name, lst);
        }
        
        lst.add(message);
        _orderedMessages.add (message);
        _messagesMap = null;
    }

    public List<Message> getMessageList()
    {
        if (_messages == null)
        {
            return Collections.unmodifiableList(Collections.<Message>emptyList());
        }
        
        return Collections.unmodifiableList(_orderedMessages);
    }

    public List<Message> getMessageList(String clientId)
    {
        if (_messages == null || !_messages.containsKey(clientId))
        {
            return Collections.unmodifiableList(Collections.<Message>emptyList());
        }
        
        return _messages.get(clientId);
    }

    public final Iterator<Message> getMessages()
    {
        if (_messages == null)
        {
            List<Message> list = Collections.emptyList();
            return list.iterator();
        }
        
        return _orderedMessages.iterator();
    }

    public final Iterator<Message> getMessages(final String clientId)
    {
        if (_messages == null || !_messages.containsKey(clientId))
        {
            List<Message> list = Collections.emptyList();
            return list.iterator();
        }
        
        return _messages.get(clientId).iterator();        
    }
    
    public final Iterator<String> getClientIdsWithMessages()
    {
        if (_messages == null || _messages.isEmpty())
        {
            List<String> list = Collections.emptyList();
            return list.iterator();
        }
        
        return _messages.keySet().iterator();
    }

    public int getMessageCount()
    {
        if (_messages != null)
        {
            return _messages.size();
        }
        return 0;
    }
    
    public Map<String, String> getMessageMap()
    {
        if (_messages != null && !_messages.isEmpty())
        {
            _messagesMap = new HashMap<String, String>();
            for (List<Message> list : _messages.values())
            {
                if (!list.isEmpty())
                {
                    Message msg = list.get(0);
                    _messagesMap.put(msg.getName(), msg.getValue());
                }
            }
        }
        else
        {
            _messagesMap = Collections.emptyMap();
        }
        return _messagesMap;
    }
}
