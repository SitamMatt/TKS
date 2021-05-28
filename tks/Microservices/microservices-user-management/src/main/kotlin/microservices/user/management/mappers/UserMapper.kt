package microservices.user.management.mappers

import core.domain.common.UserRole
import core.domain.common.valueobjects.Email
import core.domain.user.User
import microservices.user.management.dto.UserDto

fun User.toDto(): UserDto {
    val dto =  UserDto()
    dto.email = email.value
    dto.role = role.toString()
    dto.password = password
    dto.active = active
    return dto
}

fun UserDto.toDomain(): User {
    val domain = User(
        Email(email!!),
        UserRole.valueOf(role!!),
        password!!,
        active!!
    )
    return domain
}