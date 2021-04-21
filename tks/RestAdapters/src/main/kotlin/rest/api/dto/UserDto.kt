package rest.api.dto

import javax.json.bind.annotation.JsonbTransient

class UserDto(){
    var email: String? = null
    @get:JsonbTransient
    var password: String? = null
    var active: Boolean? = null
    var role: String? = null

    constructor(email: String?, password: String?, active: Boolean?, role: String?) : this() {
        this.email = email
        this.password = password
        this.active = active
        this.role = role
    }
}