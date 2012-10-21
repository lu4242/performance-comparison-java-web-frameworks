package wicketjpa.wicket;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.AbstractValidator;
import wicketjpa.entity.Booking;

public class BookPage extends TemplatePage {

    private static final String[] bedOptions = {"One king-sized bed", "Two double beds", "Three beds"};
    private static final List<Integer> bedValues = Arrays.asList(1, 2, 3);
    private static final List<Boolean> smokingValues = Arrays.asList(true, false);
    private static final String[] monthOptions = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private static final List<Integer> monthValues = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    private static final List<Integer> yearValues = Arrays.asList(2006, 2007, 2008, 2009, 2010);

    private Booking booking;

    public BookPage(Booking booking) {
        this.booking = booking;
        setDefaultModel(new CompoundPropertyModel<Booking>(booking));
        add(new Label("hotel.name"));
        add(new Label("hotel.address"));
        add(new Label("hotel.city"));
        add(new Label("hotel.state"));
        add(new Label("hotel.zip"));
        add(new Label("hotel.country"));
        add(new Label("hotel.price"));
        add(new BookingForm("form"));
    }

    private class BookingForm extends Form {

        public BookingForm(String id) {
            super(id);
            final DateField checkinDate = new DateField("checkinDate");
            final Calendar yesterday = Calendar.getInstance();
            yesterday.add(Calendar.DAY_OF_MONTH, -1);
            checkinDate.add(new AbstractValidator<Date>() {
                protected void onValidate(IValidatable<Date> v) {
                    Date date = v.getValue();
                    if (date.before(yesterday.getTime())) {
                        v.error(new ValidationError().setMessage("Check in date must be a future date"));
                    }
                }
            }).setRequired(true);
            add(new EditBorder("checkinDateBorder", checkinDate));
            DateField checkoutDate = new DateField("checkoutDate");
            checkoutDate.add(new AbstractValidator<Date>() {
                protected void onValidate(IValidatable<Date> v) {
                    if (checkinDate.isValid()) {
                        Date date = v.getValue();
                        if(date.before(checkinDate.getDate())) {
                            v.error(new ValidationError().setMessage("Check out date must be later than check in date"));
                        }
                    }
                }
            }).setRequired(true);
            add(new EditBorder("checkoutDateBorder", checkoutDate));
            FormComponent bedsChoice = new DropDownChoice<Integer>("beds", bedValues, new IChoiceRenderer<Integer>() {
                public String getDisplayValue(Integer o) {
                    return bedOptions[o - 1];
                }
                public String getIdValue(Integer o, int index) {
                    return o.toString();
                }
            }).setRequired(true);
            add(new EditBorder("bedsBorder", bedsChoice));
            RadioChoice smokingChoice = new RadioChoice<Boolean>("smoking", smokingValues, new IChoiceRenderer<Boolean>() {
                public String getDisplayValue(Boolean object) {
                    return object ? "Smoking" : "Non Smoking";
                }
                public String getIdValue(Boolean o, int index) {
                    return o.toString();
                }
            });
            add(new EditBorder("smokingBorder", smokingChoice));
            add(new EditBorder("creditCardBorder", new TextField("creditCard").setRequired(true), true));
            add(new EditBorder("creditCardNameBorder", new TextField("creditCardName").setRequired(true), true));
            EditBorder creditCardExpiryBorder = new EditBorder("creditCardExpiryBorder");
            add(creditCardExpiryBorder);
            creditCardExpiryBorder.add(new DropDownChoice<Integer>("creditCardExpiryMonth", monthValues, new IChoiceRenderer<Integer>() {
                public String getDisplayValue(Integer o) {
                    return monthOptions[o - 1];
                }
                public String getIdValue(Integer o, int index) {
                    return o.toString();
                }
            }));
            booking.setCreditCardExpiryYear(yearValues.get(0));
            creditCardExpiryBorder.add(new DropDownChoice<Integer>("creditCardExpiryYear", yearValues));
            add(new BookmarkablePageLink<Void>("cancel", MainPage.class));
        }

        @Override
        protected void onSubmit() {
            setResponsePage(new ConfirmPage(booking));
        }
    }
}
