package repository.adapters

import domain.model.context.rents.Rent
import domain.model.values.AccessionNumber
import ports.secondary.IRentRepositoryAdapter
import repository.data.RentEntity
import repository.data.ClientEntity
import repository.data.ProductEntity
import repository.mappers.RentMapper
import repository.repositories.IRepository
import java.util.*

class RentRepositoryAdapter(
    private val repository: IRepository<RentEntity>,
    private val resourceRepository: IRepository<ProductEntity>,
    private val userRepository: IRepository<ClientEntity>,
    private val mapper: RentMapper
) : IRentRepositoryAdapter {

    override fun save(rent: Rent) {
        var entity = repository.find { x: RentEntity -> x.id == rent.id }
        if (entity == null) {
            entity = mapper.mapDomainObjectToEntity(rent)!!
            val resource = resourceRepository.find { x: ProductEntity -> x.accessionNumber == rent.resourceId.value }
            val user = userRepository.find { (_, email) -> email == rent.userEmail.value }
            entity.resource = resource
            entity.user = user
            repository.add(entity)
        } else {
            // user and resource are ignored explicitly
            mapper.mapDomainObjectToEntity(rent, entity)
            repository.update(entity)
        }
    }

    override fun findActiveByResourceId(accessionNumber: AccessionNumber): Rent? {
        val entity =
            repository.find { x: RentEntity -> x.resource?.accessionNumber == accessionNumber.value && x.endDate == null }
        return mapper.mapEntityToDomainObject(entity)
    }

    override fun getById(id: UUID): Rent? {
        val entity = repository.find { x: RentEntity -> x.id == id }
        return mapper.mapEntityToDomainObject(entity)
    }
}