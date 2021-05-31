package repositories.rental.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rents")
@NamedQueries(
    NamedQuery(name = "RentEntity.findByIdentifier", query = "SELECT r FROM RentEntity r WHERE r.identifier = :id"),
    NamedQuery(
        name = "RentEntity.findByProductAccessionNumber",
        query = "SELECT r FROM RentEntity r WHERE r.product.accessionNumber = :id"
    )
)
data class RentEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(unique = true, nullable = false)
    var identifier: UUID,

    var startDate: Date,

    var endDate: Date? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    var client: ClientEntity,

    @ManyToOne(cascade = [CascadeType.ALL])
    var product: ProductEntity,
)
