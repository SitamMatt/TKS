package repositories.rental.entities

import javax.persistence.*

@Entity
@Table(name = "products")
class ProductEntity {

    @Id
    @GeneratedValue
    var id: Long = 0

    @Column(unique = true, nullable = false)
    var accessionNumber: String = ""
}