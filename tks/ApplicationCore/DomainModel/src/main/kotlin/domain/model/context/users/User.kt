package domain.model.context.users

import domain.model.UserRole
import domain.model.values.Email

data class User(
    var email: Email,
    var role: UserRole,
    var password: String,
    var active: Boolean
)
