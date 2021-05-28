package repositories.rental.entities

import javax.persistence.*

@Entity
@Table(name = "products")
@NamedQueries(
    NamedQuery(name = "ProductEntity.findByAccessionNumber", query = "SELECT p FROM ProductEntity p WHERE p.accessionNumber = :id")
)
open class ProductEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Column(unique = true, nullable = false)
    open var accessionNumber: String = ""

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "product")
    open var rents: MutableList<RentEntity> = mutableListOf()

    constructor(accessionNumber: String) : this(){
        this.accessionNumber = accessionNumber
    }
}