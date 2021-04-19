package ports.secondary

import domain.model.User

interface UserPersistencePort {
    fun save(user: User)
}