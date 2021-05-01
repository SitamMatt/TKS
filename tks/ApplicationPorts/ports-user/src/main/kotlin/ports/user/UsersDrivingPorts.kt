package ports.user

import domain.exceptions.DuplicatedEmailException
import domain.exceptions.UserNotFoundException
import domain.model.context.users.User
import domain.model.values.Email

interface UserQueryPort {
    @Throws(UserNotFoundException::class)
    fun getDetails(email: Email): User?
}

interface UserRegisterCommandPort {
    @Throws(DuplicatedEmailException::class)
    fun register(user: User)
}

interface IUserService : UserQueryPort, UserRegisterCommandPort