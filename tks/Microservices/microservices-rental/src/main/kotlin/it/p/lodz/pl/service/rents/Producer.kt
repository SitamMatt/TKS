package it.p.lodz.pl.service.rents


import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Singleton

@ApplicationScoped
class Producer {


//    private var resources: MutableList<ProductEntity>
//    private var users: MutableList<ClientEntity>
//    private var rents: MutableList<RentEntity>

    init {
//        val guid = UUID.fromString("7b4399fe-5f73-40fe-90a4-1163f3dfc221")
//        val user = ClientEntity(UUID.randomUUID(), "mszewc@edu.pl", true)
//        val user2 = ClientEntity(UUID.randomUUID(), "marcin@edu.pl", false)
//        val book = ProductEntity(UUID.randomUUID(), "EEEE-254")
//        val book2 = ProductEntity(UUID.randomUUID(), "EEEE-154")
//        val book3 = ProductEntity(UUID.randomUUID(), "EEEE-303")
//        val rent = RentEntity(UUID.randomUUID(), guid, Date(), null, user, book)
//        rents = mutableListOf(rent)
//        users = mutableListOf(user, user2)
//        resources = mutableListOf(book, book2, book3)
    }

//    @Produces
//    fun produceRentService(
//        clientAdapter: ClientSearchPort,
//        productAdapter: ProductSearchPort,
//        rentAdapter: RentRepositoryAdapter
//    ): RentService = RentService(rentAdapter, rentAdapter, clientAdapter, productAdapter)

//    @Produces
//    fun produceClientRepositoryAdapter(
//        repository: RepositoryBase<ClientEntity>,
//        mapper: ClientMapper
//    ): ClientRepositoryAdapter = ClientRepositoryAdapter(repository, mapper)

//    @Produces
//    fun produceProductRepositoryAdapter(
//        repository: RepositoryBase<ProductEntity>,
//        mapper: ProductMapper
//    ): ProductRepositoryAdapter = ProductRepositoryAdapter(repository, mapper)
//
//    @Produces
//    fun produceRentRepositoryAdapter(
//        clientRepository: RepositoryBase<ClientEntity>,
//        productRepository: RepositoryBase<ProductEntity>,
//        rentRepository: RepositoryBase<RentEntity>,
//        rentMapper: RentMapper
//    ): RentRepositoryAdapter = RentRepositoryAdapter(rentRepository, productRepository, clientRepository, rentMapper)
//
//    @Produces
//    @Singleton
//    fun produceClientRepository(): RepositoryBase<ClientEntity> = RepositoryBase(users)
//
//    @Produces
//    @Singleton
//    fun produceProductRepository(): RepositoryBase<ProductEntity> = RepositoryBase(resources)
//
//    @Produces
//    @Singleton
//    fun produceRentRepository(): RepositoryBase<RentEntity> = RepositoryBase(rents)
//
//    @Produces
//    fun produceClientMapper(): ClientMapper = ClientMapper.INSTANCE
//
//    @Produces
//    fun produceProductMapper(): ProductMapper = ProductMapper.INSTANCE
//
//    @Produces
//    fun produceRentMapper(): RentMapper = RentMapper.INSTANCE
}