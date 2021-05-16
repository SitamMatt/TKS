package domain.model.context.rents

import domain.model.values.Email

data class Client(
    var email: Email,
    var active: Boolean
)
