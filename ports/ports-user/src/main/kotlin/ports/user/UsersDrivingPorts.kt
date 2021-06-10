package ports.user

import core.domain.common.UserRole
import core.domain.common.exceptions.DuplicatedEmailException
import core.domain.common.exceptions.UserNotFoundException
import core.domain.common.valueobjects.Email
import core.domain.user.User

interface UserQueryPort {
    fun getDetails(email: Email): User?
}

interface UserRegisterCommandPort {
    @Throws(DuplicatedEmailException::class)
    fun register(user: User) : User
}

interface UserEditPort{
    @Throws(UserNotFoundException::class)
    fun changeRole(email: Email, role: UserRole)

    @Throws(UserNotFoundException::class)
    fun changeState(email: Email, state: Boolean)

}

interface IUserService : UserQueryPort, UserRegisterCommandPort, UserEditPort