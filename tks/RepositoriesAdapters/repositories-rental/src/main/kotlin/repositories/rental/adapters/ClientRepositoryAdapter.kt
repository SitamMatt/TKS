package repositories.rental.adapters

import core.domain.common.valueobjects.Email
import core.domain.rent.Client
import core.domain.rent.Rent
import ports.rent.ClientSearchPort
import repositories.rental.entities.ClientEntity
import repositories.rental.entities.RentEntity
import repositories.rental.mappers.toDomain
import java.util.*
import javax.persistence.EntityManager

class ClientRepositoryAdapter(
    private val entityManager: EntityManager
) : ClientSearchPort {

    override fun findByEmail(email: Email): Client? {
        return try {
            val query = entityManager.createNamedQuery("ClientEntity.findByEmail", ClientEntity::class.java)
            query.setParameter("email", email.value)
            val entity = query.singleResult
            entity?.toDomain();
        } catch (ex: Exception) {
            null
        }
    }
}