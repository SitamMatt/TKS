package rest.api.mappers

import common.mappers.AccessionNumberMapper
import data.AbstractResourceEntity
import org.mapstruct.MappingTarget
import data.BookEntity
import data.MagazineEntity
import domain.model.Book
import domain.model.Magazine
import domain.model.Resource
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper(uses = [AccessionNumberMapper::class])
interface ResourceMapper {
    @JvmDefault
    fun mapEntityToDomainObject(entity: AbstractResourceEntity?): Resource? = when(entity){
        is BookEntity -> mapEntityToDomainObject(entity)
        is MagazineEntity -> mapEntityToDomainObject(entity)
        else -> throw Exception() // todo specify exact name
    }
    @JvmDefault
    fun mapDomainObjectToEntity(resource: Resource?): AbstractResourceEntity? = when(resource){
        is Book -> mapDomainObjectToEntity(resource)
        is Magazine -> mapDomainObjectToEntity(resource)
        else -> throw Exception() // todo specify exact name
    }
    @JvmDefault
    fun mapDomainObjectToEntity(resource: Resource?, @MappingTarget entity: AbstractResourceEntity?) = when{
        entity is BookEntity && resource is Book -> mapDomainObjectToEntity(resource, entity)
        entity is MagazineEntity && resource is Magazine -> mapDomainObjectToEntity(resource, entity)
        else -> throw Exception() // todo specify exact name
    }

    fun mapEntityToDomainObject(entity: BookEntity?): Book?
    fun mapDomainObjectToEntity(resource: Book?): BookEntity?
    fun mapDomainObjectToEntity(resource: Book?, @MappingTarget entity: BookEntity?)
    fun mapEntityToDomainObject(entity: MagazineEntity?): Magazine?
    fun mapDomainObjectToEntity(resource: Magazine?): MagazineEntity?
    fun mapDomainObjectToEntity(resource: Magazine?, @MappingTarget entity: MagazineEntity?)

    companion object {
        val INSTANCE: ResourceMapper = Mappers.getMapper(ResourceMapper::class.java)
    }
}