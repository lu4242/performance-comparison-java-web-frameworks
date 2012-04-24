package grocketjpa.wicket

import java.util.Arrays
import java.util.Calendar
import java.util.Date
import java.util.List

import org.apache.wicket.extensions.yui.calendar.DateField
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.DropDownChoice
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.markup.html.form.RadioChoice
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.model.CompoundPropertyModel
import org.apache.wicket.validation.IValidatable
import org.apache.wicket.validation.ValidationError
import org.apache.wicket.validation.validator.AbstractValidator
import grocketjpa.entity.Booking

class BookPage extends TemplatePage {    

    BookPage(Booking booking) {
        setDefaultModel new CompoundPropertyModel(booking)
        add new Label("hotel.name")
        add new Label("hotel.address")
        add new Label("hotel.city")
        add new Label("hotel.state")
        add new Label("hotel.zip")
        add new Label("hotel.country")
        add new Label("hotel.price")
        add(new BookingForm("form", booking))
    }

    static class BookingForm extends Form {

        def static bedOptions = ["One king-sized bed", "Two double beds", "Three beds"]
        def static bedValues = [1, 2, 3]
        def static smokingValues = [true, false]
        def static monthOptions = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
        def static monthValues = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        def static yearValues = [2006, 2007, 2008, 2009, 2010]

        def booking

        BookingForm(id, booking) {
            super(id)
            this.booking = booking
            def checkinDate = new DateField("checkinDate")
            def yesterday = Calendar.getInstance()
            yesterday.add(Calendar.DAY_OF_MONTH, -1)
            checkinDate.add(new AbstractValidator() {
                void onValidate(IValidatable v) {
                    def date = v.value
                    if (date.before(yesterday.time)) {
                        v.error(new ValidationError().setMessage("Check in date must be a future date"))
                    }
                }
            }).setRequired(true)
            add new EditBorder("checkinDateBorder", checkinDate)
            def checkoutDate = new DateField("checkoutDate")
            checkoutDate.add(new AbstractValidator() {
                void onValidate(IValidatable v) {
                    if (checkinDate.valid) {
                        def date = v.value
                        if(date.before(checkinDate.date)) {
                            v.error(new ValidationError().setMessage("Check out date must be later than check in date"))
                        }
                    }
                }
            }).setRequired(true)
            add new EditBorder("checkoutDateBorder", checkoutDate)
            def bedsChoice = new DropDownChoice("beds", bedValues, new IChoiceRenderer() {
                Object getDisplayValue(Object o) {
                    bedOptions[o - 1]
                }
                String getIdValue(Object o, int index) {
                    o.toString()
                }
            }).setRequired(true)
            add new EditBorder("bedsBorder", bedsChoice)
            RadioChoice smokingChoice = new RadioChoice("smoking", smokingValues, new IChoiceRenderer() {
                Object getDisplayValue(Object o) {
                    o ? "Smoking" : "Non Smoking"
                }
                String getIdValue(Object o, int index) {
                    o.toString()
                }
            })
            add new EditBorder("smokingBorder", smokingChoice)
            add new EditBorder("creditCardBorder", new TextField("creditCard").setRequired(true), true)
            add new EditBorder("creditCardNameBorder", new TextField("creditCardName").setRequired(true), true)
            def creditCardExpiryBorder = new EditBorder("creditCardExpiryBorder")
            add creditCardExpiryBorder
            creditCardExpiryBorder.add(new DropDownChoice("creditCardExpiryMonth", monthValues, new IChoiceRenderer() {
                Object getDisplayValue(Object o) {
                    monthOptions[o - 1]
                }
                String getIdValue(Object o, int index) {
                    return o.toString()
                }
            }))
            booking.creditCardExpiryYear = yearValues.get(0)
            creditCardExpiryBorder.add new DropDownChoice("creditCardExpiryYear", yearValues)
            add new BookmarkablePageLink("cancel", MainPage.class)
        }
        
        void onSubmit() {
            setResponsePage new ConfirmPage(booking)
        }
    }
}


