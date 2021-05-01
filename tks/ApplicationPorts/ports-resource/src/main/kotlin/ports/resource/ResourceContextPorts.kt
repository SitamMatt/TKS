package ports.resource

import domain.common.valueobjects.AccessionNumber
import domain.resource.Resource

interface ResourcePersistencePort {
    fun save(resource: Resource)
    fun remove(resource: Resource)
}

interface ResourceSearchPort {
    fun findByAccessionNumber(accessionNumber: AccessionNumber): Resource?
}

interface IResourceRepositoryAdapter : ResourcePersistencePort, ResourceSearchPort
