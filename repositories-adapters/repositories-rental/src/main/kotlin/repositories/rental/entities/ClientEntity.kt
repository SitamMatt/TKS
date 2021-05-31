package repositories.rental.entities

import javax.persistence.*

@Entity
@Table(name = "clients")
@NamedQueries(
    NamedQuery(name = "ClientEntity.findByEmail", query = "SELECT c FROM ClientEntity c WHERE c.email = :email")
)
data class ClientEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(unique = true, nullable = false)
    var email: String,

    @Column(nullable = false)
    var active: Boolean = true,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "client")
    val rents: List<RentEntity>? = null
)