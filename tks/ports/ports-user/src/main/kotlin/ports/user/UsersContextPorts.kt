package ports.user

import domain.common.valueobjects.Email
import domain.user.User

interface UserPersistencePort {
    fun save(user: User)
}

interface UserSearchPort {
    fun findByEmail(email: Email): User?
}

interface IUserRepositoryAdapter : UserPersistencePort, UserSearchPort
