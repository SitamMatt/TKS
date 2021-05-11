package repositories.library.entities

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("BOOK")
open class Book : ResourceEntityTrait() {
    open lateinit var author: String
}