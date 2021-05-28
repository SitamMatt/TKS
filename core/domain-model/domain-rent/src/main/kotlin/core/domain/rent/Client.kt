package core.domain.rent

import core.domain.common.valueobjects.Email


data class Client(
    var email: Email,
    var active: Boolean
)
