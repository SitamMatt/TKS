package webservice.mappers

import common.mappers.AccessionNumberMapper
import domain.model.traits.Resource
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import webservice.dto.LibraryItemSoapDto

@Mapper(uses = [AccessionNumberMapper::class])
interface IResourceMapper{

    fun toDto(source: Resource): LibraryItemSoapDto

    companion object{
        val INSTANCE: IResourceMapper = Mappers.getMapper(IResourceMapper::class.java)
    }
}