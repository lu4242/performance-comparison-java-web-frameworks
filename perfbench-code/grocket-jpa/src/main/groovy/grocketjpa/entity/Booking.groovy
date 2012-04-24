package grocketjpa.entity

import java.math.BigDecimal
import java.text.DateFormat

import javax.persistence.*
import org.hibernate.validator.*

@Entity
class Booking implements Serializable {

    @Id
    @GeneratedValue
    Long id

    @ManyToOne
    @NotNull
    User user

    @ManyToOne
    @NotNull
    Hotel hotel

    @NotNull
    @Basic
    @Temporal(TemporalType.DATE)
    Date checkinDate

    @Basic
    @Temporal(TemporalType.DATE)
    @NotNull
    Date checkoutDate


    @NotNull(message = "Credit card number is required")
    @Length(min = 16, max = 16, message = "Credit card number must 16 digits long")
    @Pattern(regex = "^\\d*\$", message = "Credit card number must be numeric")
    String creditCard

    @NotNull(message = "Credit card name is required")
    @Length(min = 3, max = 70, message = "Credit card name is required")
    String creditCardName

    int creditCardExpiryMonth

    int creditCardExpiryYear

    boolean smoking

    int beds

    BigDecimal getTotal() {
        hotel.price.multiply(new BigDecimal(getNights()))
    }

    int getNights() {
        (checkoutDate.getTime() - checkinDate.getTime()) / 1000 / 60 / 60 / 24
    }

    String getDescription() {
        def df = DateFormat.getDateInstance(DateFormat.MEDIUM)
        hotel == null ? null : hotel.name + ", " + df.format(checkinDate) + " to " + df.format(checkoutDate)
    }

    String toString() {
        "Booking(" + user + "," + hotel + ")"
    }

}

