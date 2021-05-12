package repositories.library.mappers

import core.domain.common.valueobjects.AccessionNumber
import core.domain.resource.Book
import core.domain.resource.Magazine
import core.domain.resource.Resource
import repositories.library.entities.BookEntity
import repositories.library.entities.MagazineEntity
import repositories.library.entities.ResourceEntityTrait

fun ResourceEntityTrait.toDomain() = when (this) {
    is BookEntity -> Book(AccessionNumber(accessionNumber), title, locked, author)
    is MagazineEntity -> Magazine(AccessionNumber(accessionNumber), title, locked, publisher)
    else -> null
}

fun Resource.toEntity() = when(this){
    is Book -> BookEntity().let {
        it.id = 0
        it.accessionNumber = this.accessionNumber!!.value
        it.author = this.author
        it.locked = this.locked
        it.title = this.title
    }
    is Magazine -> MagazineEntity().let {
        it.id = 0
        it.accessionNumber = this.accessionNumber!!.value
        it.publisher = this.publisher
        it.locked = this.locked
        it.title = this.title
    }
    else -> null
}



