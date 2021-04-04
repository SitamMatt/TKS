package mappers

import data.UserEntity
import org.mapstruct.MappingTarget
import model.User
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface UserMapper {
    fun mapEntityToDomainObject(entity: UserEntity?): User?
    fun mapDomainObjectToEntity(user: User?): UserEntity?
    fun mapDomainObjectToEntity(user: User?, @MappingTarget entity: UserEntity?)

    companion object {
        val INSTANCE:UserMapper = Mappers.getMapper(UserMapper::class.java)
    }
}