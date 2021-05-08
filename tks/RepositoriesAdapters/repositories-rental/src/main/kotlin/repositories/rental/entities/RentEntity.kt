package repositories.rental.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rents")
@NamedQueries(
    NamedQuery(name = "RentEntity.findByIdentifier", query = "SELECT r FROM RentEntity r WHERE r.identifier = :id"),
    NamedQuery(name = "RentEntity.findByProductAccessionNumber", query = "SELECT r FROM RentEntity r WHERE r.product.accessionNumber = :id")
)
open class RentEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Column(unique = true, nullable = false)
    open lateinit var identifier: UUID

    open lateinit var startDate: Date

    open var endDate: Date? = null

    @ManyToOne(cascade = [CascadeType.ALL])
    open lateinit var client: ClientEntity

    @ManyToOne(cascade = [CascadeType.ALL])
    open lateinit var product: ProductEntity

    constructor(
        identifier: UUID,
        startDate: Date,
        endDate: Date?,
        client: ClientEntity,
        product: ProductEntity,
    ) : this() {
        this.identifier = identifier
        this.startDate = startDate
        this.endDate = endDate
        this.client = client
        this.product = product
    }
}
