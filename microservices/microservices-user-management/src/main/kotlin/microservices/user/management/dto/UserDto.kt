package microservices.user.management.dto

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
open class UserDto {
    open var email: String? = null
    open var role: String? = null
    open var password: String? = null
    open var active: Boolean? = null
}