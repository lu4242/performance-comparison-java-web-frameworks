package grocketjpa.wicket

import java.util.Map
import java.util.concurrent.ConcurrentHashMap
import javax.persistence.Entity
import org.apache.wicket.Component
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior
import org.apache.wicket.behavior.AbstractBehavior
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter
import org.apache.wicket.markup.ComponentTag
import org.apache.wicket.markup.html.border.Border
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.model.CompoundPropertyModel
import org.apache.wicket.model.IModel
import org.apache.wicket.validation.IValidatable
import org.apache.wicket.validation.IValidator
import org.apache.wicket.validation.ValidationError
import org.hibernate.validator.ClassValidator
import org.hibernate.validator.InvalidValue

class EditBorder extends Border {

    EditBorder(String id) {
        super(id)
        add new FeedbackPanel("message", new ContainerFeedbackMessageFilter(this))
    }

    EditBorder(String id, FormComponent fc, boolean ajax) {
        this(id)
        add(fc, ajax)
    }

    EditBorder(String id, FormComponent fc) {
        this(id, fc, false)
    }

    def add(FormComponent fc) {
        add(fc, false)
    }

    def add(FormComponent fc, boolean ajax) {
        super.add(fc)
        fc.add new EditBorderBehavior(fc)
        add(new AbstractBehavior() {            
            void onComponentTag(Component c, ComponentTag tag) {
                if (!fc.valid) {
                    tag.put("class", "input errors")
                }
            }
        })
        if(ajax) {
            setOutputMarkupId true
            fc.add(new AjaxFormComponentUpdatingBehavior("onblur") {
                void onUpdate(AjaxRequestTarget target) {
                    getFormComponent().validate()
                    target.addComponent(EditBorder.this)
                }                
                void onError(AjaxRequestTarget target, RuntimeException e) {
                    target.addComponent(EditBorder.this)
                }
            })
        }
    }

    static class EditBorderBehavior extends AbstractBehavior {

        def static final classValidatorCache = [:]

        FormComponent fc

        EditBorderBehavior(fc) {
            this.fc = fc
        }

        void beforeRender(Component c) {
            // super.beforeRender(c)
            def model = c.innermostModel
            if (model != null && model instanceof CompoundPropertyModel) {
                def clazz = model.object.class
                if (clazz.isAnnotationPresent(Entity.class)) {
                    fc.add(getValidator(clazz, fc.id))
                }
            }
        }

        IValidator getValidator(final Class clazz, final String expression) {
            new IValidator() {
                void validate(IValidatable v) {
                    def cv = classValidatorCache.get(clazz)
                    if (cv == null) {
                        cv = new ClassValidator(clazz)
                        classValidatorCache.put(clazz, cv)
                    }
                    def invalidValues = cv.getPotentialInvalidValues(expression, v.getValue())
                    if (invalidValues.length > 0) {
                        def message = invalidValues[0].getMessage()
                        v.error(new ValidationError().setMessage(message))
                    }
                }
            }
        }

    }

}


