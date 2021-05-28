package repositories.user.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
open class UserEntity() {

    @Id
    @GeneratedValue
    open var id: Long = 0

    open lateinit var email: String
    open lateinit var role: String

    constructor(email: String, role: String, password: String, active: Boolean) : this() {
        this.email = email
        this.role = role
        this.password = password
        this.active = active
    }

    open lateinit var password: String
    open var active: Boolean = false
}