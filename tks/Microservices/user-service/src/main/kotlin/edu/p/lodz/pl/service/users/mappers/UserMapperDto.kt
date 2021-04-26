package edu.p.lodz.pl.service.users.mappers

import domain.model.User
import domain.model.UserRole
import domain.model.values.Email
import edu.p.lodz.pl.service.users.dto.UserDto

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