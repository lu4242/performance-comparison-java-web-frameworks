package grocketjpa.entity

import javax.persistence.*
import org.hibernate.validator.*

@Entity
class Hotel implements Serializable {

  @Id
  @GeneratedValue
  Long id

  @Length(max = 50)
  @NotNull
  String name

  @Length(max = 100)
  @NotNull
  String address

  @Length(max = 40)
  @NotNull
  String city

  @Length(min = 2, max = 10)
  @NotNull
  String state

  @Length(min = 4, max = 6)
  @NotNull
  String zip

  @Length(min = 2, max = 40)
  @NotNull
  String country

  @Column(precision = 6, scale = 2)
  java.math.BigDecimal price

  String toString() {
    "Hotel(" + name + "," + address + "," + city + "," + zip + ")"
  }

}

