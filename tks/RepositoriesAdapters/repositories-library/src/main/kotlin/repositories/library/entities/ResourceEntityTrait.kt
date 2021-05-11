package repositories.library.entities

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
abstract class ResourceEntityTrait {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0
    open lateinit var accessionNumber: String
    open lateinit var title: String
    open var locked: Boolean = false
}