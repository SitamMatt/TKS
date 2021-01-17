package dto

import model.UserRole
import java.util.*

open class UserBaseDto {
    var guid: UUID? = null
    var active: Boolean = false
    var role: UserRole? = null
    var login: String? = null
    var firstname: String? = null
    var lastname: String? = null
}

class UserGetDto : UserBaseDto() {
}

class UserCreateDto : UserBaseDto(){
    var password: String? = null
}


