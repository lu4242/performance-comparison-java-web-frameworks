package swicketjpa.entity

import java.text.DateFormat
import java.util.Date
import javax.persistence._
import org.hibernate.validator._

@serializable
@Entity
class Booking {

  def this(hotel: Hotel, user: User) = {
    this()
    this.hotel = hotel
    this.user = user
  }

  @Id
  @GeneratedValue
  var id: Long = 0

  @ManyToOne
  @NotNull
  var user: User = null

  @ManyToOne
  @NotNull
  var hotel: Hotel = null

  @NotNull
  @Basic
  @Temporal(TemporalType.DATE)
  var checkinDate: Date = null

  @Basic
  @Temporal(TemporalType.DATE)
  @NotNull
  var checkoutDate: Date = null

  @NotNull(message = "Credit card number is required")
  @Length(min = 16, max = 16, message = "Credit card number must 16 digits long")
  @Pattern(regex = "^\\d*$", message = "Credit card number must be numeric")
  var creditCard: String = null

  @NotNull(message = "Credit card name is required")
  @Length(min = 3, max = 70, message = "Credit card name is required")
  var creditCardName: String = null

  var creditCardExpiryMonth: Int = 0

  var creditCardExpiryYear: Int = 0

  var smoking: Boolean = false

  var beds: Int = 0
  
  def getTotal = hotel.price.multiply(new java.math.BigDecimal(getNights))
  
  def getNights = (checkoutDate.getTime() - checkinDate.getTime()) / 1000 / 60 / 60 / 24
  
  def getDescription = {
    val df: DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
    if(hotel != null)
      hotel.name + ", " + df.format(checkinDate) + " to " + df.format(checkoutDate)
    else null
  }

  override def toString = "Booking(" + user + "," + hotel + ")"

}
