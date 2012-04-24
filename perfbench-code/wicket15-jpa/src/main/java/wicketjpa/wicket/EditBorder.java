package wicketjpa.wicket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Entity;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

public class EditBorder extends Border {
    
    private static final Map<Class, ClassValidator> classValidatorCache =
            new ConcurrentHashMap<Class, ClassValidator>();

    public EditBorder(String id) {
        super(id);
        addToBorder(new FeedbackPanel("message", new ContainerFeedbackMessageFilter(this)));
    }

    public EditBorder(String id, FormComponent fc, boolean ajax) {
        this(id);
        add(fc, ajax);
    }

    public EditBorder(String id, FormComponent fc) {
        this(id, fc, false);
    }

    public void add(FormComponent fc) {
        add(fc, false);
    }

    public void add(final FormComponent fc, boolean ajax) {
        super.add(fc);
        fc.add(new Behavior() {
	        @Override
	        public void onConfigure(Component component)
	        {
		        super.onConfigure(component);
                IModel model = component.getInnermostModel();
                if (model != null && model instanceof CompoundPropertyModel) {
                    CompoundPropertyModel cpm = (CompoundPropertyModel) model;
                    Class clazz = cpm.getObject().getClass();
                    if (clazz.isAnnotationPresent(Entity.class)) {
                        fc.add(getValidator(clazz, fc.getId()));
                    }
                }
            }
        });
        add(new Behavior() {
            @Override
            public void onComponentTag(Component c, ComponentTag tag) {
                if (!fc.isValid()) {
                    tag.put("class", "input errors");
                }
            } 
        });
        if(ajax) {
            setOutputMarkupId(true);
            fc.add(new AjaxFormComponentUpdatingBehavior("onblur") {
                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    getFormComponent().validate();
                    target.add(EditBorder.this);
                }
                @Override
                protected void onError(AjaxRequestTarget target, RuntimeException e) {
                    target.add(EditBorder.this);
                }
            });
        }
    }

    private IValidator getValidator(final Class clazz, final String expression) {
        return new IValidator() {
            public void validate(IValidatable v) {
                ClassValidator cv = classValidatorCache.get(clazz);
                if (cv == null) {
                    cv = new ClassValidator(clazz);
                    classValidatorCache.put(clazz, cv);
                }
                InvalidValue[] invalidValues = cv.getPotentialInvalidValues(expression, v.getValue());
                if (invalidValues.length > 0) {
                    String message = invalidValues[0].getMessage();                    
                    v.error(new ValidationError().setMessage(message));
                }
            }
        };
    }
}
