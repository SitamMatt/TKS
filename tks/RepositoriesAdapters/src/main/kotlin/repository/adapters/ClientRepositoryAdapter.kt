package repository.adapters

import domain.model.context.rents.Client
import domain.model.values.Email
import ports.secondary.ClientSearchPort
import repository.data.ClientEntity
import repository.mappers.ClientMapper
import repository.repositories.IRepository

class ClientRepositoryAdapter(
    private val repository: IRepository<ClientEntity>,
    private val mapper: ClientMapper
) : ClientSearchPort {

    override fun findByEmail(email: Email): Client? {
        val entity = repository.find { x: ClientEntity -> x.email == email.value }
        return mapper.mapEntityToDomainObject(entity)
    }
}