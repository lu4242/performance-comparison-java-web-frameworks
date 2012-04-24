package tapestryjpa.tapestry.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;

public class AjaxFormSubmit {
    
    @Parameter(defaultPrefix = BindingConstants.LITERAL, required = true)
    private String onEvent;
    
    @Parameter(defaultPrefix = BindingConstants.LITERAL, required = true)
    private String formId;

    @Environmental
    private RenderSupport renderSupport;

    void beforeRenderTemplate(final MarkupWriter writer) {
        writer.getElement().attribute(onEvent, "doAjaxFormSubmit()");
    }

    void afterRender(MarkupWriter writer) {
        renderSupport.addScript(
                  "top.doAjaxFormSubmit = function() {"
                + "  var formElement = $('"+ formId + "');"
                + "  var zoneManager = Tapestry.findZoneManager(formElement);"
                + "  var successHandler = function(transport) {"
                + "    zoneManager.processReply(transport.responseJSON);"
                + "  };"
                + "  formElement.sendAjaxRequest(formElement.action, { onSuccess : successHandler });"
                + "};");
    }

}
