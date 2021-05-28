package repositories.rental.adapters

import core.domain.common.valueobjects.Email
import core.domain.rent.Client
import ports.rent.ClientSearchPort
import repositories.rental.mappers.toDomain
import repositories.rental.repositories.ClientRepository
import javax.persistence.EntityManager

class ClientRepositoryAdapter(
    private val entityManager: EntityManager,
    private val clientRepository: ClientRepository
) : ClientSearchPort {

    override fun findByEmail(email: Email): Client? {
        return try {
            val result = clientRepository.findByEmail(email.value)
            if(!result.isPresent) return null
            return result.get().toDomain()
//            val query = entityManager.createNamedQuery("ClientEntity.findByEmail", ClientEntity::class.java)
//            query.setParameter("email", email.value)
//            val entity = query.singleResult
//            entity?.toDomain();
        } catch (ex: Exception) {
            null
        }
    }
}