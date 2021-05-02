package repositories.rental.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "clients")
class ClientEntity{

    @Id
    @GeneratedValue
    var id: Long = 0

    @Column(unique = true, nullable = false)
    var email: String = ""

    @Column(nullable = false)
    var active: Boolean = true
}