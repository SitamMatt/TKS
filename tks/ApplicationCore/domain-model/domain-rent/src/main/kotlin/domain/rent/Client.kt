package domain.rent

import domain.common.valueobjects.Email

data class Client(
    var email: Email,
    var active: Boolean
)
