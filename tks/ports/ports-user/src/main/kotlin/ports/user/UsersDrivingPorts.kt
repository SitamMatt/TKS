package ports.user

import core.domain.common.exceptions.DuplicatedEmailException
import core.domain.common.exceptions.UserNotFoundException
import domain.common.valueobjects.Email
import core.domain.user.User

interface UserQueryPort {
    @Throws(core.domain.common.exceptions.UserNotFoundException::class)
    fun getDetails(email: Email): User?
}

interface UserRegisterCommandPort {
    @Throws(core.domain.common.exceptions.DuplicatedEmailException::class)
    fun register(user: User)
}

interface IUserService : UserQueryPort, UserRegisterCommandPort