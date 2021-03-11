package dto

import java.util.*

class TokenDto{
    constructor(token: String?, expires: String?) {
        this.token = token
        this.expires = expires
    }

    var token: String? = null
    var expires: String? = null
}