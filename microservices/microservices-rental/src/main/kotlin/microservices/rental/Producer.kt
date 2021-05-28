import core.services.rental.RentalService
import ports.rent.ClientSearchPort
import ports.rent.IRentalService
import ports.rent.ProductSearchPort
import repositories.rental.adapters.ClientRepositoryAdapter
import repositories.rental.adapters.ProductRepositoryAdapter
import repositories.rental.adapters.RentRepositoryAdapter
import repositories.rental.repositories.ClientRepository
import repositories.rental.repositories.ProductRepository
import repositories.rental.repositories.RentRepository
import javax.enterprise.context.RequestScoped
import javax.enterprise.inject.Default
import javax.enterprise.inject.Produces
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.PersistenceUnit

@Singleton
open class Producer {

//    @Produces
//    @PersistenceContext(unitName = "rentalPU")
//    private lateinit var em: EntityManager

    @PersistenceUnit(unitName = "rentalPU")
    private lateinit var entityManagerFactory: EntityManagerFactory

    @Produces
    @Default
    @RequestScoped
    open fun create(): EntityManager = entityManagerFactory.createEntityManager()

    @Produces
    fun produceRentService(
        clientRepositoryAdapter: ClientSearchPort,
        productRepositoryAdapter: ProductSearchPort,
        rentRepositoryAdapter: RentRepositoryAdapter,
    ): IRentalService =
        RentalService(rentRepositoryAdapter, rentRepositoryAdapter, clientRepositoryAdapter, productRepositoryAdapter)

    @Produces
    fun produceRentRepository(
        entityManager: EntityManager,
        clientRepository: ClientRepository,
        productRepository: ProductRepository,
        rentRepository: RentRepository
    ): RentRepositoryAdapter {
        return RentRepositoryAdapter(entityManager, rentRepository, clientRepository, productRepository)
    }

    @Produces
    fun produceClientRepository(
        entityManager: EntityManager,
        clientRepository: ClientRepository
    ): ClientRepositoryAdapter {
        return ClientRepositoryAdapter(entityManager, clientRepository)
    }

    @Produces
    fun produceProductRepository(
        entityManager: EntityManager,
        productRepository: ProductRepository
    ): ProductRepositoryAdapter = ProductRepositoryAdapter(entityManager, productRepository)
}
//
//
////    private var resources: MutableList<ProductEntity>
////    private var users: MutableList<ClientEntity>
////    private var rents: MutableList<RentEntity>
//
//    init {
////        val guid = UUID.fromString("7b4399fe-5f73-40fe-90a4-1163f3dfc221")
////        val user = ClientEntity(UUID.randomUUID(), "mszewc@edu.pl", true)
////        val user2 = ClientEntity(UUID.randomUUID(), "marcin@edu.pl", false)
////        val book = ProductEntity(UUID.randomUUID(), "EEEE-254")
////        val book2 = ProductEntity(UUID.randomUUID(), "EEEE-154")
////        val book3 = ProductEntity(UUID.randomUUID(), "EEEE-303")
////        val rent = RentEntity(UUID.randomUUID(), guid, Date(), null, user, book)
////        rents = mutableListOf(rent)
////        users = mutableListOf(user, user2)
////        resources = mutableListOf(book, book2, book3)
//    }
//
////    @Produces
////    fun produceRentService(
////        clientAdapter: ClientSearchPort,
////        productAdapter: ProductSearchPort,
////        rentAdapter: RentRepositoryAdapter
////    ): RentService = RentService(rentAdapter, rentAdapter, clientAdapter, productAdapter)
//
////    @Produces
////    fun produceClientRepositoryAdapter(
////        repository: RepositoryBase<ClientEntity>,
////        mapper: ClientMapper
////    ): ClientRepositoryAdapter = ClientRepositoryAdapter(repository, mapper)
//
////    @Produces
////    fun produceProductRepositoryAdapter(
////        repository: RepositoryBase<ProductEntity>,
////        mapper: ProductMapper
////    ): ProductRepositoryAdapter = ProductRepositoryAdapter(repository, mapper)
////
////    @Produces
////    fun produceRentRepositoryAdapter(
////        clientRepository: RepositoryBase<ClientEntity>,
////        productRepository: RepositoryBase<ProductEntity>,
////        rentRepository: RepositoryBase<RentEntity>,
////        rentMapper: RentMapper
////    ): RentRepositoryAdapter = RentRepositoryAdapter(rentRepository, productRepository, clientRepository, rentMapper)
////
////    @Produces
////    @Singleton
////    fun produceClientRepository(): RepositoryBase<ClientEntity> = RepositoryBase(users)
////
////    @Produces
////    @Singleton
////    fun produceProductRepository(): RepositoryBase<ProductEntity> = RepositoryBase(resources)
////
////    @Produces
////    @Singleton
////    fun produceRentRepository(): RepositoryBase<RentEntity> = RepositoryBase(rents)
////
////    @Produces
////    fun produceClientMapper(): ClientMapper = ClientMapper.INSTANCE
////
////    @Produces
////    fun produceProductMapper(): ProductMapper = ProductMapper.INSTANCE
////
////    @Produces
////    fun produceRentMapper(): RentMapper = RentMapper.INSTANCE
//}