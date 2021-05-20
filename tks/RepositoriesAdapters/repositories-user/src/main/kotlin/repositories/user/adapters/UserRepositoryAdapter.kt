package repositories.user.adapters

import core.domain.common.valueobjects.Email
import core.domain.user.User
import ports.user.IUserRepositoryAdapter
import repositories.user.mappers.toDomain
import repositories.user.repositories.UserRepository

class UserRepositoryAdapter(
    private val repository: UserRepository
) : IUserRepositoryAdapter {
    override fun save(user: User) {
        TODO("Not yet implemented")
    }

    override fun findByEmail(email: Email): User? {
        val param = email.value
        val entity = repository.findByEmail(param)
        if (entity == null) return null
        return entity.toDomain()
    }
}