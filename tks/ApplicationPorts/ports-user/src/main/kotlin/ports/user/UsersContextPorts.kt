package ports.user

import domain.model.context.users.User
import domain.model.values.Email

interface UserPersistencePort {
    fun save(user: User)
}

interface UserSearchPort {
    fun findByEmail(email: Email): User?
}

interface IUserRepositoryAdapter : UserPersistencePort, UserSearchPort
