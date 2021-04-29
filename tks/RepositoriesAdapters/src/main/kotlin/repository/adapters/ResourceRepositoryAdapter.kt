package repository.adapters

import domain.model.context.library.Resource
import domain.model.values.AccessionNumber
import ports.secondary.IResourceRepositoryAdapter
import repository.data.AbstractResourceEntity
import repository.mappers.ResourceMapper
import repository.repositories.IRepository

class ResourceRepositoryAdapter(
    private val repository: IRepository<AbstractResourceEntity>,
    private val mapper: ResourceMapper
) : IResourceRepositoryAdapter {

    override fun save(resource: Resource) {
        val entity = repository.find { x: AbstractResourceEntity -> x.accessionNumber == resource.accessionNumber?.value }
        if(entity == null){
            repository.add(mapper.mapDomainObjectToEntity(resource)!!)
        }else{
            mapper.mapDomainObjectToEntity(resource, entity)
            repository.update(entity)
        }
    }

    override fun remove(resource: Resource) {
        val entity = repository.find { x: AbstractResourceEntity -> x.accessionNumber == resource.accessionNumber?.value } ?: throw Exception()
        repository.remove(entity)
    }

    override fun findByAccessionNumber(accessionNumber: AccessionNumber): Resource? {
        val entity = repository.find { x: AbstractResourceEntity -> x.accessionNumber == accessionNumber.value }
        return mapper.mapEntityToDomainObject(entity)
    }
}