package repositories.library.entities

import javax.persistence.*

@Entity
@DiscriminatorValue("MAGAZINE")
open class MagazineEntity : ResourceEntityTrait(){
    open lateinit var publisher: String
}