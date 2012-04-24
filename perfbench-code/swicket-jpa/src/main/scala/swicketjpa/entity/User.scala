package swicketjpa.entity

import javax.persistence._
import org.hibernate.validator._

@serializable
@Entity
@Table(name = "Customer")
class User {

  @Id
  @Length(min = 5, max = 15)
  @Pattern(regex = "^\\w*$", message = "not a valid username")
  var username: String = null

  @NotNull
  @Length(min = 5, max = 15)
  var password: String = null

  @NotNull
  @Length(max = 100)
  var name: String = null

  override def toString = "User(" + username + ")"

}
