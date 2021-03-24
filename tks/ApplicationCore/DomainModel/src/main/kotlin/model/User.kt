package model

data class User(
    var email: String,
    var role: UserRole,
    var password: String,
    var active: Boolean
)
