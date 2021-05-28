package ports.user

import core.domain.common.exceptions.DuplicatedEmailException
import core.domain.common.valueobjects.Email
import core.domain.user.User

interface UserQueryPort {
    fun getDetails(email: Email): User?
}

interface UserRegisterCommandPort {
    @Throws(DuplicatedEmailException::class)
    fun register(user: User) : User
}

interface IUserService : UserQueryPort, UserRegisterCommandPort