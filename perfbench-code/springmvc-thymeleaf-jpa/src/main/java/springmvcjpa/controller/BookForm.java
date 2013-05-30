/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package springmvcjpa.controller;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import springmvcjpa.entity.Booking;

/**
 *
 * @author lu4242
 */
public class BookForm
{
    private Date checkinDate;
    private Date checkoutDate;
    private String creditCard;
    private String creditCardName;
    private int creditCardExpiryMonth;
    private int creditCardExpiryYear;
    private boolean smoking;
    private int beds;

    public BookForm()
    {
    }

    public BookForm(Booking booking)
    {
        this.checkinDate = booking.getCheckinDate();
        this.checkoutDate = booking.getCheckoutDate();
        this.creditCard = booking.getCreditCard();
        this.creditCardName = booking.getCreditCardName();
        this.creditCardExpiryMonth = booking.getCreditCardExpiryMonth();
        this.creditCardExpiryYear = booking.getCreditCardExpiryYear();
        this.smoking = booking.isSmoking();
        this.beds = booking.getBeds();
    }
    
    public void merge(Booking booking)
    {
        booking.setCheckinDate(this.getCheckinDate());
        booking.setCheckoutDate(this.getCheckoutDate());
        booking.setCreditCard(this.getCreditCard());
        booking.setCreditCardName(this.getCreditCardName());
        booking.setSmoking(this.isSmoking());
        booking.setBeds(this.getBeds());
        booking.setCreditCardExpiryMonth(this.getCreditCardExpiryMonth());
        booking.setCreditCardExpiryYear(this.getCreditCardExpiryYear());
    }

    @NotNull
    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date datetime) {
        this.checkinDate = datetime;
    }

    @NotNull
    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    @NotNull(message = "Credit card number is required")
    @Size(min = 16, max = 16, message = "Credit card number must 16 digits long")
    @Pattern(regexp = "^\\d*$", message = "Credit card number must be numeric")
    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public boolean isSmoking() {
        return smoking;
    }

    public void setSmoking(boolean smoking) {
        this.smoking = smoking;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    @NotNull(message = "Credit card name is required")
    @Size(min = 3, max = 70, message = "Credit card name is required")
    public String getCreditCardName() {
        return creditCardName;
    }

    public void setCreditCardName(String creditCardName) {
        this.creditCardName = creditCardName;
    }

    public int getCreditCardExpiryMonth() {
        return creditCardExpiryMonth;
    }

    public void setCreditCardExpiryMonth(int creditCardExpiryMonth) {
        this.creditCardExpiryMonth = creditCardExpiryMonth;
    }

    public int getCreditCardExpiryYear() {
        return creditCardExpiryYear;
    }

    public void setCreditCardExpiryYear(int creditCardExpiryYear) {
        this.creditCardExpiryYear = creditCardExpiryYear;
    }
}
