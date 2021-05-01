package ports.resource

import domain.model.context.library.Resource
import domain.model.values.AccessionNumber

interface ResourcePersistencePort {
    fun save(resource: Resource)
    fun remove(resource: Resource)
}

interface ResourceSearchPort {
    fun findByAccessionNumber(accessionNumber: AccessionNumber): Resource?
}

interface IResourceRepositoryAdapter : ResourcePersistencePort, ResourceSearchPort
