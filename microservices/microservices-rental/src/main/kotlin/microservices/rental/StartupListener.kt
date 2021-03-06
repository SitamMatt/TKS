package microservices.rental

import repositories.rental.entities.ClientEntity
import repositories.rental.entities.ProductEntity
import repositories.rental.entities.RentEntity
import java.util.*
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.Persistence
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener

@WebListener
class StartupListener : ServletContextListener {

    override fun contextInitialized(servletContextEvent: ServletContextEvent) {
        val emf = Persistence.createEntityManagerFactory("rentalPU")
        val em = emf.createEntityManager()
        em.transaction.begin()
        val guid = UUID.fromString("0b41fc23-83bb-46d9-a1a1-70eb750482cf")
        val client = ClientEntity(0, "mszewc@edu.pl", true)
        val product = ProductEntity(0, "EEEE-154")
        val product2 = ProductEntity(0, "EEEE-254")
        val rent = RentEntity(0, guid, Date(), null, client, product)
        em.persist(client)
        em.persist(product)
        em.persist(product2)
        em.persist(rent)
        em.transaction.commit()
        em.clear()
    }
}