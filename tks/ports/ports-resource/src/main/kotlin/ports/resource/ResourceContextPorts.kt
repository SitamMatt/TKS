package ports.resource

import core.domain.common.valueobjects.AccessionNumber
import core.domain.resource.Resource

interface ResourcePersistencePort {
    fun save(resource: Resource)
    fun remove(resource: Resource)
}

interface ResourceSearchPort {
    fun findByAccessionNumber(accessionNumber: core.domain.common.valueobjects.AccessionNumber): Resource?
}

interface IResourceRepositoryAdapter : ResourcePersistencePort, ResourceSearchPort
