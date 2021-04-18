package application.services

import domain.exceptions.DuplicatedEmailException
import domain.exceptions.UserNotFoundException
import domain.model.User
import domain.model.UserRole
import domain.model.values.Email
import ports.primary.combined.IUserService
import ports.secondary.UserPersistencePort
import ports.secondary.UserSearchPort

open class UserService(
    private val userPersistencePort: UserPersistencePort,
    private val userSearchPort: UserSearchPort
) : IUserService {

    @Throws(DuplicatedEmailException::class)
    override fun register(user: User) {
        val duplicate = userSearchPort.findByEmail(user.email)
        if (duplicate != null) throw DuplicatedEmailException()
        userPersistencePort.add(user)
    }

    @Throws(UserNotFoundException::class)
    open fun changeRole(email: Email, role: UserRole) {
        val user = userSearchPort.findByEmail(email) ?: throw UserNotFoundException()
        if (user.role != role) {
            user.role = role
            userPersistencePort.update(user)
        }
    }

    @Throws(UserNotFoundException::class)
    open fun changeState(email: Email, state: Boolean) {
        val user = userSearchPort.findByEmail(email) ?: throw UserNotFoundException()
        if (!user.active == state) {
            user.active = state
            userPersistencePort.update(user)
        }
    }

    @Throws(UserNotFoundException::class)
    override fun getDetails(email: Email): User {
        return userSearchPort.findByEmail(email) ?: throw UserNotFoundException()
    }
}