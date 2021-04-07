package domain.model

import domain.model.values.Email

data class User(
    var email: Email,
    var role: UserRole,
    var password: String,
    var active: Boolean
)
