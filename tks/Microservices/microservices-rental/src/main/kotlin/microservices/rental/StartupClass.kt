//package microservices.rental
//
//import repositories.rental.entities.ClientEntity
//import repositories.rental.entities.RentEntity
//import repositories.rental.repositories.ClientRepository
//import repositories.rental.repositories.ProductRepository
//import repositories.rental.repositories.RentRepository
//import java.util.*
//import javax.annotation.PostConstruct
//import javax.ejb.Singleton
//import javax.ejb.Startup
//import javax.inject.Inject
//import repositories.rental.entities.ProductEntity as ProductEntity1
//
//@Startup
//@Singleton
//open class StartupClass {
//
//    @Inject
//    private lateinit var clientRepository: ClientRepository
//
//    @Inject
//    private lateinit var productRepository: ProductRepository
//
//    @Inject
//    private lateinit var rentRepository: RentRepository
//
//    @PostConstruct
//    fun init() {
//        val guid = UUID.fromString("0b41fc23-83bb-46d9-a1a1-70eb750482cf")
//        val client = ClientEntity("mszewc@edu.pl", true)
//        val product = ProductEntity1("EEEE-154")
//        val product2 = ProductEntity1("EEEE-254")
//        val rent = RentEntity(guid, Date(), null, client, product)
//        clientRepository.save(client)
//        productRepository.save(product)
//        productRepository.save(product2)
//        rentRepository.save(rent)
//    }
//}