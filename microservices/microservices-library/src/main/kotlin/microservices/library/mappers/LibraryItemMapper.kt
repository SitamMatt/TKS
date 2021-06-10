package microservices.library.mappers

import core.domain.common.valueobjects.AccessionNumber
import core.domain.resource.Book
import core.domain.resource.Magazine
import core.domain.resource.Resource
import microservices.library.dto.LibraryResourceDto
import microservices.library.dto.LibraryResourceMessageDto
import microservices.library.dto.LibraryResourceType

fun LibraryResourceDto.toDomain(): Resource = when(type){
    LibraryResourceType.BOOK -> {
        val id = if (accessionNumber == null)  null else AccessionNumber(accessionNumber!!)
        Book(id, title, locked ?: false, author!!)
    }
    LibraryResourceType.MAGAZINE -> {
        val id = if (accessionNumber == null)  null else AccessionNumber(accessionNumber!!)
        Magazine(id, title, locked ?: false, publisher!!)
    }
    else -> throw Exception()
}

fun Resource.toDto(): LibraryResourceDto = when(this){
    is Book -> LibraryResourceDto(accessionNumber!!.value, locked, title, author, null, LibraryResourceType.BOOK)
    is Magazine -> LibraryResourceDto(accessionNumber!!.value, locked, title, null, publisher, LibraryResourceType.MAGAZINE)
    else -> throw Exception()
}

fun Resource.toMessage(): LibraryResourceMessageDto = when(this){
    is Book -> LibraryResourceMessageDto(accessionNumber!!.value, locked, title, author, null, LibraryResourceType.BOOK)
    is Magazine -> LibraryResourceMessageDto(accessionNumber!!.value, locked, title, null, publisher, LibraryResourceType.MAGAZINE)
    else -> throw Exception()
}