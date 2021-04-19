package repository.adapters

import domain.model.Rent
import domain.model.values.AccessionNumber
import ports.secondary.RentPersistencePort
import ports.secondary.RentSearchPort
import repository.data.AbstractResourceEntity
import repository.data.RentEntity
import repository.data.UserEntity
import repository.mappers.RentMapper
import repository.repositories.IRepository

class RentRepositoryAdapter(
    private val repository: IRepository<RentEntity>,
    private val resourceRepository: IRepository<AbstractResourceEntity>,
    private val userRepository: IRepository<UserEntity>,
    private val mapper: RentMapper
) : RentSearchPort, RentPersistencePort {

    override fun save(rent: Rent) {
        var entity = repository.find { x: RentEntity -> x.id == rent.id }
        if (entity == null) {
            entity = mapper.mapDomainObjectToEntity(rent)!!
            val resource = resourceRepository.find { x: AbstractResourceEntity -> x.accessionNumber == rent.resourceId.value }
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
}