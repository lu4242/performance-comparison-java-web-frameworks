package swicketjpa.wicket

import java.util.Arrays
import java.util.Calendar
import org.apache.wicket.extensions.yui.calendar.DateField
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form._
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.model.CompoundPropertyModel
import org.apache.wicket.validation._
import org.apache.wicket.validation.validator.AbstractValidator
import swicketjpa.entity.Booking

object BookPage {
  val bedOptions = Array("One king-sized bed", "Two double beds", "Three beds")
  val bedValues: java.util.List[Int] = Arrays.asList(1, 2, 3)
  val smokingValues: java.util.List[Boolean] = Arrays.asList(true, false);
  val monthOptions = Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
  val monthValues: java.util.List[Int] = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
  val yearValues: java.util.List[Int] = Arrays.asList(2006, 2007, 2008, 2009, 2010)
}

class BookPage(booking: Booking) extends TemplatePage {

  setDefaultModel(new CompoundPropertyModel(booking))
  add(new Label("hotel.name"))
  add(new Label("hotel.address"))
  add(new Label("hotel.city"))
  add(new Label("hotel.state"))
  add(new Label("hotel.zip"))
  add(new Label("hotel.country"))
  add(new Label("hotel.price"))
  add(new Form("form") {
    val checkinDate = new DateField("checkinDate")
    val yesterday = Calendar.getInstance()
    yesterday.add(Calendar.DAY_OF_MONTH, -1)
    checkinDate.add(new AbstractValidator[java.util.Date]() {
      override def onValidate(v: IValidatable[java.util.Date]) = {        
        if (v.getValue().before(yesterday.getTime())) {
          v.error(new ValidationError().setMessage("Check in date must be a future date"))
        }
      }
    }).setRequired(true)
    add(new EditBorder("checkinDateBorder", checkinDate))
    val checkoutDate = new DateField("checkoutDate")
    checkoutDate.add(new AbstractValidator[java.util.Date]() {
      override def onValidate(v: IValidatable[java.util.Date]) = {
        if (checkinDate.isValid()) {
          if(v.getValue().before(checkinDate.getDate())) {
            v.error(new ValidationError().setMessage("Check out date must be later than check in date"))
          }
        }
      }
    }).setRequired(true);
    add(new EditBorder("checkoutDateBorder", checkoutDate))
    val bedsChoice = new DropDownChoice("beds", BookPage.bedValues, new IChoiceRenderer[Int]() {
      override def getDisplayValue(choice: Int) = BookPage.bedOptions(choice - 1)
      override def getIdValue(choice: Int, index: Int) = choice.toString
    }).setRequired(true)
    add(new EditBorder("bedsBorder", bedsChoice))
    val smokingChoice = new RadioChoice("smoking", BookPage.smokingValues, new IChoiceRenderer[Boolean]() {
      override def getDisplayValue(choice: Boolean) = if (choice) "Smoking" else "Non Smoking"
      override def getIdValue(choice: Boolean, index: Int) = choice.toString
    })
    add(new EditBorder("smokingBorder", smokingChoice))
    add(new EditBorder("creditCardBorder", new TextField("creditCard").setRequired(true), true))
    add(new EditBorder("creditCardNameBorder", new TextField("creditCardName").setRequired(true), true))
    val creditCardExpiryBorder = new EditBorder("creditCardExpiryBorder")
    add(creditCardExpiryBorder)
    creditCardExpiryBorder.add(new DropDownChoice("creditCardExpiryMonth", BookPage.monthValues, new IChoiceRenderer[Int]() {
      override def getDisplayValue(choice: Int) = BookPage.monthOptions(choice - 1)
      override def getIdValue(choice: Int, index: Int) = choice.toString
    }))
    booking.creditCardExpiryYear = BookPage.yearValues.get(0)
    creditCardExpiryBorder.add(new DropDownChoice("creditCardExpiryYear", BookPage.yearValues))
    add(new BookmarkablePageLink("cancel", classOf[MainPage]))

    override def onSubmit = setResponsePage(new ConfirmPage(booking))

  })

}
