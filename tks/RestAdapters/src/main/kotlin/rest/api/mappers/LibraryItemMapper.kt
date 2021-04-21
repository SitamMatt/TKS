package rest.api.mappers

import rest.api.dto.LibraryItemDto
import domain.model.Book
import domain.model.Magazine
import domain.model.traits.Resource
import domain.model.values.AccessionNumber
import org.mapstruct.factory.Mappers

class LibraryItemMapper {

    fun toDomainObject(src: LibraryItemDto?): Resource = when(src?.type){
        "Book" -> toBook(src)
        "Magazine" -> toMagazine(src)
        else -> throw Exception() // todo specify exact name
    }

    fun toBook(src: LibraryItemDto): Book = Book(
        AccessionNumber(src.accessionNumber!!),
        src.title!!,
        src.author!!
    )
    fun toMagazine(src: LibraryItemDto): Magazine = Magazine(
        AccessionNumber(src.accessionNumber!!),
        src.title!!,
        src.publisher!!
    )

    fun toDto(source: Resource): LibraryItemDto = when(source){
        is Book -> toDto(source)
        is Magazine -> toDto(source)
        else -> throw Exception() // todo specify exact name
    }

    fun toDto(src: Book): LibraryItemDto = LibraryItemDto(
        src.accessionNumber?.value,
        src.title,
        src.author,
        null,
        "BOOK"
    )
    fun toDto(src: Magazine): LibraryItemDto = LibraryItemDto(
        src.accessionNumber?.value,
        src.title,
        null,
        src.publisher,
        "MAGAZINE"
    )

    companion object{
        val INSTANCE: LibraryItemMapper = LibraryItemMapper()
    }

}