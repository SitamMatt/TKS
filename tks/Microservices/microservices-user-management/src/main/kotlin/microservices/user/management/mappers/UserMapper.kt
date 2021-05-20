package microservices.user.management.mappers

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