package domain.user

import domain.common.UserRole
import domain.common.valueobjects.Email

data class User(
    var email: Email,
    var role: UserRole,
    var password: String,
    var active: Boolean
)
