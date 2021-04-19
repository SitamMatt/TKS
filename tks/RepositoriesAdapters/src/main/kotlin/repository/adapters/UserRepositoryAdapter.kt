package repository.adapters

import domain.model.User
import domain.model.values.Email
import ports.secondary.UserPersistencePort
import ports.secondary.UserSearchPort
import repository.data.UserEntity
import repository.mappers.UserMapper
import repository.repositories.IRepository

class UserRepositoryAdapter(
    private val repository: IRepository<UserEntity>,
    private val mapper: UserMapper
) : UserPersistencePort, UserSearchPort {

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