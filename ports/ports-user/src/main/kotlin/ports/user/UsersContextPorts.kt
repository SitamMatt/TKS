package ports.user

import core.domain.common.valueobjects.Email
import core.domain.user.User

interface UserPersistencePort {
    fun save(user: User)
}

interface UserSearchPort {
    fun findByEmail(email: Email): User?
}

interface IUserRepositoryAdapter : UserPersistencePort, UserSearchPort
