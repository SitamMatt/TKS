package repositories.rental.entities

import javax.persistence.*

@Entity
@Table(name = "clients")
@NamedQueries(
    NamedQuery(name = "ClientEntity.findByEmail", query = "SELECT c FROM ClientEntity c WHERE c.email = :email")
)
open class ClientEntity(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Column(unique = true, nullable = false)
    open var email: String = ""

    @Column(nullable = false)
    open var active: Boolean = true

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "client")
    open var rents: MutableList<RentEntity> = mutableListOf()

    constructor(email: String, active: Boolean) : this() {
        this.email = email
        this.active = active
    }
}