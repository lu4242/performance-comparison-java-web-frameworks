import java.text.MessageFormat

class FormValTagLib {

    def formval = { attrs ->
        def name = attrs.name
        def bean = attrs.bean
        def value = bean."${name}" ?: ""
        def markupId = name + "_error"
        def url = g.createLink(controller: "formVal")
        def parameters = "{ beanClass: '${bean.class.name}', name: '${name}', value: this.value }"
        def onblur = "new Ajax.Updater('${markupId}', '${url}', {parameters: ${parameters}})"
        def type = attrs.type ?: "text"
        out << """<input name="${name}" type="${type}" value="${value}" onblur="${onblur}"/>"""
        def error = bean.errors.getFieldError(name)
        def message = error ? MessageFormat.format(error.defaultMessage, error.arguments) : ''
        out << """<span id="${markupId}" class="errors">${message}</span>"""
    }

    def formmsg = { attrs ->
        def error = attrs.bean.errors.getFieldError(attrs.name)
        def message = error ? MessageFormat.format(error.defaultMessage, error.arguments) : ''
        out << """<span class="errors">${message}</span>"""
    }
	
}
