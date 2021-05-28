//package microservices.rental
//
//import org.apache.deltaspike.core.api.provider.BeanProvider
//import repositories.rental.entities.ClientEntity
//import repositories.rental.entities.ProductEntity
//import repositories.rental.entities.RentEntity
//import repositories.rental.repositories.ClientRepository
//import repositories.rental.repositories.ProductRepository
//import repositories.rental.repositories.RentRepository
//import java.util.*
//import javax.inject.Inject
//import javax.persistence.EntityManager
//import javax.persistence.Persistence
//import javax.persistence.PersistenceContext
//import javax.servlet.ServletContextEvent
//import javax.servlet.ServletContextListener
//import javax.servlet.annotation.WebListener
//
//@WebListener
//class CustomServletContextListener : ServletContextListener {
//
//    @Inject
//    private lateinit var beanManager: EntityManager
//    @Inject
//    private lateinit var clientRepository: ClientRepository
//    @Inject
//    private lateinit var productRepository: ProductRepository
//    @Inject
//    private lateinit var rentRepository: RentRepository
//
////    @PersistenceContext(unitName = "rentalPU")
////    private lateinit var em: EntityManager
//
//    override fun contextInitialized(servletContextEvent: ServletContextEvent) {
////        var emf = Persistence.createEntityManagerFactory("rentalPU")
////        var em = emf.createEntityManager()
////        em.transaction.begin()
////        BeanProvider.get
//        servletContextEvent.servletContext
//        val guid = UUID.fromString("0b41fc23-83bb-46d9-a1a1-70eb750482cf")
//        val client = ClientEntity("mszewc@edu.pl", true)
//        val product = ProductEntity("EEEE-154")
//        val product2 = ProductEntity("EEEE-254")
//        val rent = RentEntity(guid, Date(), null, client, product)
//        clientRepository.save(client)
//        productRepository.save(product)
//        productRepository.save(product2)
//        rentRepository.save(rent)
//        beanManager.transaction.commit()
////        em.persist(client)
////        em.persist(product)
////        em.persist(product2)
////        em.persist(rent)
////        em.transaction.commit()
////        em.clear()
//    }
//
//    override fun contextDestroyed(servletContextEvent: ServletContextEvent) {
//        // Context shutdown
//    }
//}