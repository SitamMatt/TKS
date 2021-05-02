package repositories.rental.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rents")
class RentEntity {

    @Id
    @GeneratedValue
    var id: Long = 0

    @Column(unique = true, nullable = false)
    lateinit var identifier: UUID

    lateinit var startDate: Date

    var endDate: Date? = null

    @ManyToOne
    lateinit var user: ClientEntity

    @ManyToOne
    lateinit var resource: ProductEntity
}
