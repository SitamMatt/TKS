package dto

import model.UserRole
import java.util.*
import javax.validation.constraints.*

open class UserBaseDto {
    var active: Boolean = false
    var role: UserRole? = null
    @NotNull(message = "Login cannot be null. ")
    @Size(min = 4, message = "Login must must be at least 4 characters long. ")
    var login: String? = null
    var firstname: String? = null
    var lastname: String? = null
}

class UserGetDto : UserBaseDto() {
    var guid: UUID? = null
}


