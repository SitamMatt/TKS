package rest.api.mappers

import common.mappers.AccessionNumberMapper
import rest.api.dto.LibraryItemDto
import domain.model.Book
import domain.model.Magazine
import domain.model.Resource
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper(uses = [AccessionNumberMapper::class])
interface LibraryItemMapper {

    @JvmDefault
    fun toDomainObject(source: LibraryItemDto): Resource = when(source.getType()){
        "Book" -> toBook(source)
        "Magazine" -> toMagazine(source)
        else -> throw Exception() // todo specify exact name
    }

    fun toBook(source: LibraryItemDto): Book
    fun toMagazine(source: LibraryItemDto): Magazine


    @JvmDefault
    fun toDto(source: Resource): LibraryItemDto = when(source){
        is Book -> toDto(source)
        is Magazine -> toDto(source)
        else -> throw Exception() // todo specify exact name
    }

    fun toDto(source: Book): LibraryItemDto
    fun toDto(source: Magazine): LibraryItemDto


    companion object{
        val INSTANCE: LibraryItemMapper = Mappers.getMapper(LibraryItemMapper::class.java)
    }

}