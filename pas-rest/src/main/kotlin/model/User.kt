package model


class User constructor() : Entity(){
    var isActive = false
    var role: UserRole? = null
    var firstname: String? = null
    var lastname: String? = null
    var login: String? = null
    var password: String? = null
}