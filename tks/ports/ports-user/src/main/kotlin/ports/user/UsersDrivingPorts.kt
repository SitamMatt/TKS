package ports.user

import domain.common.exceptions.DuplicatedEmailException
import domain.common.exceptions.UserNotFoundException
import domain.common.valueobjects.Email
import domain.user.User

interface UserQueryPort {
    @Throws(UserNotFoundException::class)
    fun getDetails(email: Email): User?
}

interface UserRegisterCommandPort {
    @Throws(DuplicatedEmailException::class)
    fun register(user: User)
}

interface IUserService : UserQueryPort, UserRegisterCommandPort