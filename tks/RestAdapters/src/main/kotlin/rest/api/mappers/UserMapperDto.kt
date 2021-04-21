package rest.api.mappers

import common.mappers.EmailMapper
import rest.api.dto.UserDto
import domain.model.User
import domain.model.UserRole
import domain.model.values.Email
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

class UserMapperDto {
    fun toDomainObject(src: UserDto?): User? = if(src == null) null else User(
        Email(src.email!!),
        UserRole.valueOf(src.role!!),
        src.password!!,
        src.active!!
    )
    fun toDto(src: User): UserDto? = if(src == null) null else UserDto().apply {
        email = src.email.value
        role = src.role.toString()
        active = src.active
    }

    companion object {
        val INSTANCE:UserMapperDto = UserMapperDto()
    }
}