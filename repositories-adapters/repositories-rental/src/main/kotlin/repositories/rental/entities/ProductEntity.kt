package repositories.rental.entities

import javax.persistence.*

@Entity
@Table(name = "products")
@NamedQueries(
    NamedQuery(
        name = "ProductEntity.findByAccessionNumber",
        query = "SELECT p FROM ProductEntity p WHERE p.accessionNumber = :id"
    )
)
data class ProductEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(unique = true, nullable = false)
    var accessionNumber: String,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "product")
    val rents: List<RentEntity>? = null
)