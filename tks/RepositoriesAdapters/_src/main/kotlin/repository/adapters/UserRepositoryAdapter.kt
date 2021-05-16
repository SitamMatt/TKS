package repository.adapters

import domain.model.context.users.User
import domain.model.values.Email
import ports.secondary.IUserRepositoryAdapter
import repository.data.UserEntity
import repository.mappers.UserMapper
import repository.repositories.IRepository

class UserRepositoryAdapter(
    private val repository: IRepository<UserEntity>,
    private val mapper: UserMapper
) : IUserRepositoryAdapter {

    override fun findByEmail(email: Email): User? {
        val entity = repository.find { x: UserEntity -> x.email == email.value }
        return mapper.mapEntityToDomainObject(entity)
    }

    override fun save(user: User) {
        val entity = repository.find { x: UserEntity -> x.email == user.email.value }
        if (entity == null) {
            repository.add(mapper.mapDomainObjectToEntity(user)!!)
        } else {
            mapper.mapDomainObjectToEntity(user, entity)
            repository.update(entity)
        }
    }
}