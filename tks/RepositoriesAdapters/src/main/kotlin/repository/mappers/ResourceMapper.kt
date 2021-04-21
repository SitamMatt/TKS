package repository.mappers

import domain.model.Book
import domain.model.Magazine
import domain.model.traits.Resource
import domain.model.values.AccessionNumber
import repository.data.AbstractResourceEntity
import repository.data.BookEntity
import repository.data.MagazineEntity

class ResourceMapper {
    fun mapEntityToDomainObject(src: AbstractResourceEntity?): Resource? = when (src) {
        is BookEntity -> mapEntityToDomainObject(src)
        is MagazineEntity -> mapEntityToDomainObject(src)
        else -> null
    }

    fun mapDomainObjectToEntity(src: Resource?): AbstractResourceEntity? = when (src) {
        is Book -> mapDomainObjectToEntity(src)
        is Magazine -> mapDomainObjectToEntity(src)
        else -> null
    }

    fun mapDomainObjectToEntity(src: Resource?, target: AbstractResourceEntity) = when (target) {
        is BookEntity -> mapDomainObjectToEntity(src, target)
        is MagazineEntity -> mapDomainObjectToEntity(src, target)
        else -> throw Exception() // todo specify exact name
    }

    fun mapEntityToDomainObject(src: BookEntity?): Book? = if (src == null) null else Book(
        AccessionNumber(src.accessionNumber!!),
        src.title,
        src.author
    )

    fun mapDomainObjectToEntity(src: Book?): BookEntity? = if (src == null) null else BookEntity(
        null,
        src.accessionNumber?.value,
        src.title,
        src.author
    )

    fun mapDomainObjectToEntity(src: Resource?, target: BookEntity) {
        src ?: return
        if (src is Book) target.apply {
            accessionNumber = src.accessionNumber?.value
            author = src.author
            title = src.title
        } else throw Exception() // todo specify exact name
    }

    fun mapEntityToDomainObject(src: MagazineEntity?): Magazine? = if (src == null) null else Magazine(
        AccessionNumber(src.accessionNumber!!),
        src.title,
        src.publisher
    )

    fun mapDomainObjectToEntity(src: Magazine?): MagazineEntity? = if (src == null) null else MagazineEntity(
        null,
        src.accessionNumber?.value,
        src.title,
        src.publisher
    )

    fun mapDomainObjectToEntity(src: Resource?, target: MagazineEntity) {
        src ?: return
        if (src is Magazine) target.apply {
            accessionNumber = src.accessionNumber?.value
            title = src.title
            publisher = src.publisher
        } else throw Exception() // todo specify exact name
    }

    companion object {
        val INSTANCE: ResourceMapper = ResourceMapper()
    }
}