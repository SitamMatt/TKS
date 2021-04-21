package repository.mappers

import repository.data.UserEntity
import domain.model.User
import domain.model.UserRole
import domain.model.values.Email
import org.mapstruct.MappingTarget

class UserMapper {
    fun mapEntityToDomainObject(src: UserEntity?): User? = if(src == null) null else User(
        Email(src.email),
        UserRole.valueOf(src.role),
        src.password,
        src.active
    )

    fun mapDomainObjectToEntity(src: User?): UserEntity? = if (src == null) null else UserEntity(
        null,
        src.email.value,
        src.role.toString(),
        src.password,
        src.active
    )
    fun mapDomainObjectToEntity(src: User?, target: UserEntity){
        src ?: return
        target.email = src.email.value
        target.role = src.role.toString()
        target.password = src.password
        target.active = src.active
    }

    companion object {
        val INSTANCE: UserMapper = UserMapper()
    }
}