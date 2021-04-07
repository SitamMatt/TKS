package rest.api.dto

class LoginDto() {
    var email: String? = null
    var password: String? = null

    constructor(email: String?, password: String?) : this() {
        this.email = email
        this.password = password
    }
}