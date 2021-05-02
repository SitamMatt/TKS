package core.services.users.management

import core.domain.common.UserRole
import core.domain.common.exceptions.DuplicatedEmailException
import core.domain.common.exceptions.UserNotFoundException
import domain.common.valueobjects.Email
import core.domain.user.User
import ports.user.IUserService
import ports.user.UserPersistencePort
import ports.user.UserSearchPort

open class UsersManagementService(
    private val userPersistencePort: UserPersistencePort,
    private val userSearchPort: UserSearchPort
) : IUserService {

    @Throws(core.domain.common.exceptions.DuplicatedEmailException::class)
    override fun register(user: User) {
        val duplicate = userSearchPort.findByEmail(user.email)
        if (duplicate != null) throw core.domain.common.exceptions.DuplicatedEmailException()
        userPersistencePort.save(user)
    }

    @Throws(core.domain.common.exceptions.UserNotFoundException::class)
    open fun changeRole(email: Email, role: core.domain.common.UserRole) {
        val user = userSearchPort.findByEmail(email) ?: throw core.domain.common.exceptions.UserNotFoundException()
        if (user.role != role) {
            user.role = role
            userPersistencePort.save(user)
        }
    }

    @Throws(core.domain.common.exceptions.UserNotFoundException::class)
    open fun changeState(email: Email, state: Boolean) {
        val user = userSearchPort.findByEmail(email) ?: throw core.domain.common.exceptions.UserNotFoundException()
        if (!user.active == state) {
            user.active = state
            userPersistencePort.save(user)
        }
    }

    @Throws(core.domain.common.exceptions.UserNotFoundException::class)
    override fun getDetails(email: Email): User {
        return userSearchPort.findByEmail(email) ?: throw core.domain.common.exceptions.UserNotFoundException()
    }
}