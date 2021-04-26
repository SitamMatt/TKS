package webservice.mappers

import domain.model.context.library.Book
import domain.model.context.library.Magazine
import domain.model.context.library.Resource
import webservice.dto.LibraryItemSoapDto

class ResourceMapper {

    fun toDto(src: Resource?): LibraryItemSoapDto? = when (src) {
        is Book -> toDto(src)
        is Magazine -> toDto(src)
        else -> null
    }

    private fun toDto(src: Book): LibraryItemSoapDto = LibraryItemSoapDto().apply {
        accessionNumber = src.accessionNumber?.value
        author = src.author
        title = src.title
        type = "BOOK"
    }

    private fun toDto(src: Magazine): LibraryItemSoapDto = LibraryItemSoapDto().apply {
        accessionNumber = src.accessionNumber?.value
        publisher = src.publisher
        title = src.title
        type = "MAGAZINE"
    }

    companion object {
        val INSTANCE: ResourceMapper = ResourceMapper()
    }
}