package ports.secondary

import domain.model.User

interface UserPersistencePort {
    fun add(user: User)
    fun update(user: User)
}