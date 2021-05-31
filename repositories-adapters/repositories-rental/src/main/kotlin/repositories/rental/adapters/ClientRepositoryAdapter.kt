package repositories.rental.adapters

import core.domain.common.valueobjects.Email
import core.domain.rent.Client
import ports.rent.IClientRepositoryAdapter
import repositories.rental.mappers.toDomain
import repositories.rental.mappers.toEntity
import repositories.rental.repositories.ClientRepository

class ClientRepositoryAdapter(
    private val clientRepository: ClientRepository
) : IClientRepositoryAdapter {

    override fun findByEmail(email: Email): Client? {
        return try {
            val result = clientRepository.findByEmail(email.value)
            if (!result.isPresent) return null
            return result.get().toDomain()
        } catch (ex: Exception) {
            null
        }
    }

    fun save(client: Client) {
        try {
            val result = clientRepository.findByEmail(client.email.value)
            if (!result.isPresent) {
                val entity = client.toEntity()
                clientRepository.save(entity)
            } else {
                val entity = result.get()
                client.toEntity(entity)
                clientRepository.save(entity)
            }
        } catch (ex: Exception) {
            throw ex
        }
    }
}