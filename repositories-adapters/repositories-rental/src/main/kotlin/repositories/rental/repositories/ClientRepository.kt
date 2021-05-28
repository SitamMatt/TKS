package repositories.rental.repositories

import org.apache.deltaspike.data.api.EntityRepository
import org.apache.deltaspike.data.api.Repository
import org.apache.deltaspike.jpa.api.transaction.Transactional
import repositories.rental.entities.ClientEntity
import java.util.*

@Transactional
@Repository(forEntity = ClientEntity::class)
interface ClientRepository : EntityRepository<ClientEntity, Long> {

    fun findByEmail(email: String): Optional<ClientEntity>
}