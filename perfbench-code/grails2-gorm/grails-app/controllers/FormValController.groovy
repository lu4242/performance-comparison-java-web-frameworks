import java.text.MessageFormat

class FormValController {
	
    def static classCache = [:]
    
    def index = {
    	def bean = classCache.get(params.beanClass)
    	if(!bean) {    		
            def beanClass = Thread.currentThread().contextClassLoader.loadClass(params.beanClass)
            bean = beanClass.newInstance()
            classCache.put(params.beanClass, bean)
    	}   
    	def name = params.name
        def constraint = bean.constraints."${name}"
        def errors = bean.errors
        constraint.validate(bean, params.value, errors)
        if(errors.hasErrors()) {
            def error = errors.getFieldError(name)
            render MessageFormat.format(error.defaultMessage, error.arguments)
        } else {
            render ''
        }
    }
    
}
