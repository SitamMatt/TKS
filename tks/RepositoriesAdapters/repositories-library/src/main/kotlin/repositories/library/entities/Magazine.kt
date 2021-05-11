package repositories.library.entities

import javax.persistence.*

@Entity
@DiscriminatorValue("MAGAZINE")
open class Magazine : ResourceEntityTrait(){
    open lateinit var publisher: String
}