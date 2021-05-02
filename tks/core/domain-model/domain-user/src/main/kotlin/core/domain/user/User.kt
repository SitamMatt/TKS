package core.domain.user

import core.domain.common.valueobjects.Email

data class User(
    var email: Email,
    var role: core.domain.common.UserRole,
    var password: String,
    var active: Boolean
)
