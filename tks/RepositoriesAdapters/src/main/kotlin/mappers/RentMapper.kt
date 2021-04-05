package mappers

import mappers.AccessionNumberMapper
import data.RentEntity
import model.Rent
import org.mapstruct.MappingTarget
import mappers.RentMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper(uses = [EmailMapper::class])
interface RentMapper {
    fun mapEntityToDomainObject(entity: RentEntity?): Rent?
    fun mapDomainObjectToEntity(user: Rent?): RentEntity?
    fun mapDomainObjectToEntity(user: Rent?, @MappingTarget entity: RentEntity?)

    companion object {
        val INSTANCE = Mappers.getMapper(RentMapper::class.java)
    }
}