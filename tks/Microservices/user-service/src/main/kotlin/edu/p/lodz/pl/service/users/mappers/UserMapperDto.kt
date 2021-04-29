package edu.p.lodz.pl.service.users.mappers

import domain.model.context.users.User
import domain.model.UserRole
import domain.model.values.Email
import edu.p.lodz.pl.service.users.dto.UserDto
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
open class UserMapperDto {
    open fun toDomainObject(src: UserDto?): User? = if (src == null) null else User(
        Email(src.email!!),
        UserRole.valueOf(src.role!!),
        src.password!!,
        src.active!!
    )

    open fun toDto(src: User): UserDto = UserDto().apply {
        email = src.email.value
        role = src.role.toString()
        active = src.active
    }
}