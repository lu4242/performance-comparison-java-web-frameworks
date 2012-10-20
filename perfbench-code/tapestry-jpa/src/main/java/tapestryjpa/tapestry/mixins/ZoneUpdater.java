/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tapestryjpa.tapestry.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 *
 * @author Leonardo Uribe
 */
@Import(library =
{
    "context:js/ZoneUpdater.js"
})
public class ZoneUpdater
{

    // Parameters
    /**
     * The event to listen for on the client. If not specified, zone update can
     * only be triggered manually through calling updateZone on the JS object.
     */
    @Parameter(name = "clientEvent", defaultPrefix = BindingConstants.LITERAL)
    private String clientEvent;
    /**
     * The event to listen for in your component class
     */
    @Parameter(name = "event", defaultPrefix = BindingConstants.LITERAL, required = true)
    private String event;
    @Parameter(name = "prefix", defaultPrefix = BindingConstants.LITERAL, value = "default")
    private String prefix;
    @Parameter(name = "context")
    private Object[] context;
    /**
     * The zone to be updated by us.
     */
    @Parameter(name = "zone", defaultPrefix = BindingConstants.LITERAL, required = true)
    private String zone;
    /**
     * Set secure to true if https is being used, else set to false.
     */
    @Parameter(name = "secure", defaultPrefix = BindingConstants.LITERAL, value = "false")
    private boolean secure;
    // Useful bits and pieces
    @Inject
    private ComponentResources componentResources;
    @Environmental
    private JavaScriptSupport javaScriptSupport;
    /**
     * The element we attach ourselves to
     */
    @InjectContainer
    private ClientElement clientElement;

    // The code
    void afterRender()
    {
        String listenerURI = componentResources.createEventLink(event, context).toAbsoluteURI(secure);

        // Add some JavaScript to the page to instantiate a ZoneUpdater. It will run when the DOM has been fully loaded.

        JSONObject spec = new JSONObject();
        spec.put("elementId", clientElement.getClientId());
        spec.put("clientEvent", clientEvent);
        spec.put("listenerURI", listenerURI);
        spec.put("zoneId", zone);
        javaScriptSupport.addScript("%sZoneUpdater = new ZoneUpdater(%s)", prefix, spec.toString());
    }
}
