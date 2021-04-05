package model

import model.values.Email

data class User(
    var email: Email,
    var role: UserRole,
    var password: String,
    var active: Boolean
)
