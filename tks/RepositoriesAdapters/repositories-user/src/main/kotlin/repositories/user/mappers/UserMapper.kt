package repositories.user.mappers

import core.domain.common.UserRole
import core.domain.common.valueobjects.Email
import core.domain.user.User
import repositories.user.entities.UserEntity


fun UserEntity.toDomain() = User(Email(email), UserRole.valueOf(role), password, active)

fun User.toEntity() = UserEntity(email.value, role.toString(), password, active)
fun User.toEntity(target: UserEntity) {
    target.email = email.value
    target.role = role.toString()
    target.password = password
    target.active = active
}
