package repositories.library.adapters

import core.domain.common.valueobjects.AccessionNumber
import core.domain.resource.Resource
import ports.resource.IResourceRepositoryAdapter
import repositories.library.mappers.toDomain
import repositories.library.mappers.toEntity
import repositories.library.repositories.ResourceRepository
import javax.inject.Inject

class ResourceRepositoryAdapter : IResourceRepositoryAdapter {

    @Inject
    private lateinit var resourceRepository: ResourceRepository

    override fun save(resource: Resource) {
        val existingEntity = resourceRepository.find(resource.accessionNumber!!.value)
        if (!existingEntity.isPresent) {
            resourceRepository.save(resource.toEntity())
        } else {
            val entity = resource.toEntity()
            entity.id = existingEntity.get().id
            resourceRepository.update(entity)
        }
    }

    override fun remove(resource: Resource) {
        val existingEntity = resourceRepository.find(resource.accessionNumber!!.value)
        if(!existingEntity.isPresent) throw Exception()
        resourceRepository.delete(existingEntity.get())
    }

    override fun findByAccessionNumber(accessionNumber: AccessionNumber): Resource? {
        val entity = resourceRepository.find(accessionNumber.value)
        if (!entity.isPresent) return null
        return entity.get().toDomain()
    }
}