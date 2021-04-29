package it.p.lodz.pl.service.library

import application.services.ResourcesService
import application.services.UserService
import ports.secondary.IResourceRepositoryAdapter
import ports.secondary.IUserRepositoryAdapter
import repository.adapters.ResourceRepositoryAdapter
import repository.data.AbstractResourceEntity
import repository.data.BookEntity
import repository.mappers.ResourceMapper
import repository.repositories.RepositoryBase
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Singleton

@ApplicationScoped
class Producer {

    private var resources: MutableList<AbstractResourceEntity>

    init {
        val book = BookEntity(UUID.randomUUID(), "EEEE-254", "Diuna", false, "Frank Herbert")
        val book2 = BookEntity(UUID.randomUUID(), "EEEE-154", "Elantris", false, "Brandon Sanderson")
        val book3 = BookEntity(UUID.randomUUID(), "EEEE-303", "Mr. Mercedes", false, "Stephen King")
        resources = mutableListOf(book, book2, book3)
    }

    @Produces
    fun produceResourceService(
        resourceAdapter: IResourceRepositoryAdapter,
    ): ResourcesService = ResourcesService(resourceAdapter, resourceAdapter)

    @Produces
    fun produceResourceRepositoryAdapter(
        repository: RepositoryBase<AbstractResourceEntity>,
        mapper: ResourceMapper
    ): ResourceRepositoryAdapter = ResourceRepositoryAdapter(repository, mapper)

    @Produces
    @Singleton
    fun produceResourceRepository(): RepositoryBase<AbstractResourceEntity> = RepositoryBase(resources)

    @Produces
    fun produceResourceMapper(): ResourceMapper = ResourceMapper.INSTANCE
}