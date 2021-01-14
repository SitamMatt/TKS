package model


class User constructor() : Entity(){
    var isActive = false
    var role: UserRole? = null
    var firstName: String? = null
    var lastName: String? = null
    var login: String? = null
    var password: String? = null
}