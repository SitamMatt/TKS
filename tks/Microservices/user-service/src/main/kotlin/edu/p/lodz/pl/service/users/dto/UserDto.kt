package edu.p.lodz.pl.service.users.dto

import javax.json.bind.annotation.JsonbTransient

data class UserDto(
    var email: String? = null,
    @get:JsonbTransient var password: String? = null,
    var active: Boolean? = null,
    var role: String? = null
)