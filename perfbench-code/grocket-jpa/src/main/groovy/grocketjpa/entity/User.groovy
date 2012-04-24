package grocketjpa.entity

import javax.persistence.*
import org.hibernate.validator.*

@Entity
@Table(name = "Customer")
class User implements Serializable {

    @Id
    @Length(min = 5, max = 15)
    @Pattern(regex = "^\\w*\$", message = "not a valid username")
    String username

    @NotNull
    @Length(min = 5, max = 15)
    String password

    @NotNull
    @Length(max = 100)
    String name

    String toString() {
        "User(" + username + ")"
    }
	
}

