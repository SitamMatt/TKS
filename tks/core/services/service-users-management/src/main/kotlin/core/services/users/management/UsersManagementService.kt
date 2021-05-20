package core.services.users.management

import core.domain.common.UserRole
import core.domain.common.exceptions.DuplicatedEmailException
import core.domain.common.exceptions.UserNotFoundException
import core.domain.common.valueobjects.Email
import core.domain.user.User
import ports.user.IUserService
import ports.user.UserPersistencePort
import ports.user.UserSearchPort

open class UsersManagementService(
    private val userPersistencePort: UserPersistencePort,
    private val userSearchPort: UserSearchPort
) : IUserService {

    @Throws(DuplicatedEmailException::class)
    override fun register(user: User) {
        val duplicate = userSearchPort.findByEmail(user.email)
        if (duplicate != null) throw DuplicatedEmailException()
        userPersistencePort.save(user)
    }

    @Throws(UserNotFoundException::class)
    open fun changeRole(email: Email, role: UserRole) {
        val user = userSearchPort.findByEmail(email) ?: throw UserNotFoundException()
        if (user.role != role) {
            user.role = role
            userPersistencePort.save(user)
        }
    }

    @Throws(UserNotFoundException::class)
    open fun changeState(email: Email, state: Boolean) {
        val user = userSearchPort.findByEmail(email) ?: throw UserNotFoundException()
        if (!user.active == state) {
            user.active = state
            userPersistencePort.save(user)
        }
    }

    override fun getDetails(email: Email): User? {
        return userSearchPort.findByEmail(email)
    }
}