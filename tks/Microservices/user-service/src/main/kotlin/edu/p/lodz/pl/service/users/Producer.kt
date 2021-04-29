package edu.p.lodz.pl.service.users

import application.services.UserService
import ports.secondary.IUserRepositoryAdapter
import repository.adapters.UserRepositoryAdapter
import repository.data.UserEntity
import repository.mappers.UserMapper
import repository.repositories.RepositoryBase
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Singleton

@ApplicationScoped
class Producer {


    private var users: MutableList<UserEntity>

    init {
        val user = UserEntity(UUID.randomUUID(), "mszewc@edu.pl", "ADMIN", "password", true)
        val user2 = UserEntity(UUID.randomUUID(), "marcin@edu.pl", "CLIENT", "password", false)
        users = mutableListOf(user, user2)
    }

    @Produces
    fun produceUserService(adapter: IUserRepositoryAdapter): UserService = UserService(adapter, adapter)

    @Produces
    fun produceUserRepositoryAdapter(
        repository: RepositoryBase<UserEntity>,
        mapper: UserMapper
    ): UserRepositoryAdapter = UserRepositoryAdapter(repository, mapper)

    @Produces
    @Singleton
    fun produceUserRepository(): RepositoryBase<UserEntity> = RepositoryBase(users)

    @Produces
    fun produceUserMapper(): UserMapper = UserMapper.INSTANCE
}