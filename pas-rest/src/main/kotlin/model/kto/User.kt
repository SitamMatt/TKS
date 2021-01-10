package model.kto

import model.Entity
import model.UserRole
import java.util.*


class User constructor() : Entity(){
    var isActive = false
    var role: UserRole? = null
    var firstName: String? = null
    var lastName: String? = null
    var login: String? = null
    var password: String? = null

    constructor(guid: UUID?, isActive: Boolean, role: UserRole?, firstName: String?, lastName: String?, login: String?, password: String?) : this() {
        this.guid = guid
        this.isActive = isActive
        this.role = role
        this.firstName = firstName
        this.lastName = lastName
        this.login = login
        this.password = password
    }

    fun map(user: User) {
        firstName = user.firstName
        lastName = user.lastName
        role = user.role
        login = user.login
        password = user.password
        isActive = user.isActive
    }
}