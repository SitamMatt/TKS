package rest.api.mappers

import domain.model.context.users.User
import domain.model.UserRole
import domain.model.values.Email
import rest.api.dto.UserDto

class UserMapperDto {
    fun toDomainObject(src: UserDto?): User? = if (src == null) null else User(
        Email(src.email!!),
        UserRole.valueOf(src.role!!),
        src.password!!,
        src.active!!
    )

    fun toDto(src: User): UserDto = UserDto().apply {
        email = src.email.value
        role = src.role.toString()
        active = src.active
    }
}