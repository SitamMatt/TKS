package dto

import model.UserRole
import java.util.*

open class UserBaseDto {
    var active: Boolean = false
    var role: UserRole? = null
    var login: String? = null
    var firstname: String? = null
    var lastname: String? = null
}

class UserGetDto : UserBaseDto() {
    var guid: UUID? = null
}


