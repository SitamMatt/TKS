package rest.api

import application.services.RentService
import application.services.ResourcesService
import application.services.UserService
import ports.secondary.combined.IResourceRepositoryAdapter
import ports.secondary.combined.IUserRepositoryAdapter
import repository.adapters.RentRepositoryAdapter
import repository.adapters.ResourceRepositoryAdapter
import repository.adapters.UserRepositoryAdapter
import repository.data.AbstractResourceEntity
import repository.data.BookEntity
import repository.data.RentEntity
import repository.data.UserEntity
import repository.mappers.RentMapper
import repository.mappers.ResourceMapper
import repository.mappers.UserMapper
import repository.repositories.RepositoryBase
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Singleton

@ApplicationScoped
class Producer {


    private var resources: MutableList<AbstractResourceEntity>
    private var users: MutableList<UserEntity>
    private var rents: MutableList<RentEntity>

    init {
        val guid = UUID.fromString("7b4399fe-5f73-40fe-90a4-1163f3dfc221")
        val user = UserEntity(UUID.randomUUID(), "mszewc@edu.pl", "ADMIN", "password", true)
        val book = BookEntity(UUID.randomUUID(), "EEEE-254", "Diuna", "Frank Herbert")
        val rent = RentEntity(UUID.randomUUID(), guid, Date(), null, user, book)
        rents = mutableListOf(rent)
        users = mutableListOf(user)
        resources = mutableListOf(book)
    }

    @Produces
    fun produceUserService(adapter: IUserRepositoryAdapter): UserService = UserService(adapter, adapter)

    @Produces
    fun produceResourceService(
        resourceAdapter: IResourceRepositoryAdapter,
        rentRepositoryAdapter: RentRepositoryAdapter
    ): ResourcesService = ResourcesService(resourceAdapter, resourceAdapter, rentRepositoryAdapter)

    @Produces
    fun produceRentService(
        userAdapter: IUserRepositoryAdapter,
        resourceAdapter: IResourceRepositoryAdapter,
        rentAdapter: RentRepositoryAdapter
    ): RentService = RentService(rentAdapter, rentAdapter, userAdapter, resourceAdapter)

    @Produces
    fun produceUserRepositoryAdapter(
        repository: RepositoryBase<UserEntity>,
        mapper: UserMapper
    ): UserRepositoryAdapter = UserRepositoryAdapter(repository, mapper)

    @Produces
    fun produceResourceRepositoryAdapter(
        repository: RepositoryBase<AbstractResourceEntity>,
        mapper: ResourceMapper
    ): ResourceRepositoryAdapter = ResourceRepositoryAdapter(repository, mapper)

    @Produces
    fun produceRentRepositoryAdapter(
        userRepository: RepositoryBase<UserEntity>,
        resourceRepository: RepositoryBase<AbstractResourceEntity>,
        rentRepository: RepositoryBase<RentEntity>,
        rentMapper: RentMapper
    ): RentRepositoryAdapter = RentRepositoryAdapter(rentRepository, resourceRepository, userRepository, rentMapper)

    @Produces
    @Singleton
    fun produceUserRepository(): RepositoryBase<UserEntity> = RepositoryBase(users)

    @Produces
    @Singleton
    fun produceResourceRepository(): RepositoryBase<AbstractResourceEntity> = RepositoryBase(resources)

    @Produces
    @Singleton
    fun produceRentRepository(): RepositoryBase<RentEntity> = RepositoryBase(rents)

    @Produces
    fun produceUserMapper(): UserMapper = UserMapper.INSTANCE

    @Produces
    fun produceResourceMapper(): ResourceMapper = ResourceMapper.INSTANCE

    @Produces
    fun produceRentMapper(): RentMapper = RentMapper.INSTANCE
}