package swicketjpa.wicket

import javax.persistence.Entity
import java.util.concurrent.ConcurrentHashMap
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
import org.apache.wicket.validation._
import org.hibernate.validator.ClassValidator

object EditBorder {
  val classValidatorCache = new ConcurrentHashMap[Class[_], ClassValidator[_]]()
}

class EditBorder(id: String) extends Border(id) {

  add(new FeedbackPanel("message", new ContainerFeedbackMessageFilter(this)))

  def this(id: String, fc: FormComponent[_], ajax: Boolean) = {
    this(id)
    add(fc, ajax)
  }

  def this(id: String, fc: FormComponent[_]) = this(id, fc, false)

  def add(fc: FormComponent[_]): Unit = add(fc, false)

  def add[T](fc: FormComponent[T], ajax: Boolean) = {
    super.add(fc)
    fc.add(new AbstractBehavior() {
      override def beforeRender(c: Component) = {
        super.beforeRender(c)
        val model = c.getInnermostModel()
        if (model != null && model.isInstanceOf[CompoundPropertyModel[_]]) {
          val cpm = model.asInstanceOf[CompoundPropertyModel[AnyRef]]
          val clazz = cpm.getObject.getClass          
          if (clazz.isAnnotationPresent(classOf[Entity])) {            
            fc.add(getValidator[T](clazz, fc.getId()))
          }
        }
      }
    })
    super.add(new AbstractBehavior() {
      override def onComponentTag(c: Component, tag: ComponentTag) =
        if (!fc.isValid()) tag.put("class", "input errors")
    })
    if(ajax) {
      setOutputMarkupId(true)
      fc.add(new AjaxFormComponentUpdatingBehavior("onblur") {
        override def onUpdate(target: AjaxRequestTarget) = {
          getFormComponent().validate()
          target.addComponent(EditBorder.this)
        }
        override def onError(target: AjaxRequestTarget, e: RuntimeException) =
          target.addComponent(EditBorder.this)        
      })
    }
  }

  def getValidator[T](clazz: Class[_], expression: String) = {
    new IValidator[T]() {
      override def validate(v: IValidatable[T]) = {
        var cv = EditBorder.classValidatorCache.get(clazz)
        if (cv == null) {
          cv = new ClassValidator(clazz)
          EditBorder.classValidatorCache.put(clazz, cv)
        }
        val invalidValues = cv.getPotentialInvalidValues(expression, v.getValue())        
        if (invalidValues.length > 0) {
          val message = invalidValues(0).getMessage()
          v.error(new ValidationError().setMessage(message))
        }
      }
    }
  }

}
