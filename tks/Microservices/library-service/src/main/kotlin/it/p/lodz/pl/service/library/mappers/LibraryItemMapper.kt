package it.p.lodz.pl.service.library.mappers

import domain.exceptions.UnknownResourceException
import domain.model.context.library.Book
import domain.model.context.library.Magazine
import domain.model.context.library.Resource
import domain.model.values.AccessionNumber
import it.p.lodz.pl.service.library.dto.LibraryItemDto

class LibraryItemMapper {

    fun toDomainObject(src: LibraryItemDto?): Resource = when(src?.type){
        "BOOK" -> toBook(src)
        "MAGAZINE" -> toMagazine(src)
        else -> throw UnknownResourceException() // todo specify exact name
    }

    fun toBook(src: LibraryItemDto): Book = Book(
        if (src.accessionNumber == null) null else AccessionNumber(src.accessionNumber!!),
        src.title!!,
        false,
        src.author!!
    )
    fun toMagazine(src: LibraryItemDto): Magazine = Magazine(
        if (src.accessionNumber == null) null else AccessionNumber(src.accessionNumber!!),
        src.title!!,
        false,
        src.publisher!!
    )

    fun toDto(source: Resource): LibraryItemDto = when(source){
        is Book -> toDto(source)
        is Magazine -> toDto(source)
        else -> throw UnknownResourceException() // todo specify exact name
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