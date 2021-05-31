import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
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

    @PersistenceUnit(unitName = "rentalPU")
    private lateinit var entityManagerFactory: EntityManagerFactory

    @Produces
    @Default
    @RequestScoped
    open fun create(): EntityManager = entityManagerFactory.createEntityManager()

    @Produces
    fun produceJsonMapper(): ObjectMapper = ObjectMapper()
        .registerModule(KotlinModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    @Produces
    fun produceRentService(
        clientRepositoryAdapter: ClientSearchPort,
        productRepositoryAdapter: ProductSearchPort,
        rentRepositoryAdapter: RentRepositoryAdapter,
    ): IRentalService =
        RentalService(rentRepositoryAdapter, rentRepositoryAdapter, clientRepositoryAdapter, productRepositoryAdapter)

    @Produces
    fun produceRentRepository(
        clientRepository: ClientRepository,
        productRepository: ProductRepository,
        rentRepository: RentRepository
    ): RentRepositoryAdapter = RentRepositoryAdapter(rentRepository, clientRepository, productRepository)

    @Produces
    fun produceClientRepository(
        clientRepository: ClientRepository
    ): ClientRepositoryAdapter = ClientRepositoryAdapter(clientRepository)

    @Produces
    fun produceProductRepository(
        productRepository: ProductRepository
    ): ProductRepositoryAdapter = ProductRepositoryAdapter(productRepository)
}
