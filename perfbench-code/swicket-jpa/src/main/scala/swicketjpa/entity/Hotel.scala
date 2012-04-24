package swicketjpa.entity

import javax.persistence._
import org.hibernate.validator._

@serializable
@Entity
class Hotel {

  @Id
  @GeneratedValue
  var id: Long = 0

  @Length(max = 50)
  @NotNull
  var name: String = null

  @Length(max = 100)
  @NotNull
  var address: String = null

  @Length(max = 40)
  @NotNull
  var city: String = null

  @Length(min = 2, max = 10)
  @NotNull
  var state: String = null

  @Length(min = 4, max = 6)
  @NotNull
  var zip: String = null

  @Length(min = 2, max = 40)
  @NotNull
  var country: String = null

  @Column(precision = 6, scale = 2)
  var price: java.math.BigDecimal = null

  override def toString = "Hotel(" + name + "," + address + "," + city + "," + zip + ")"

}
