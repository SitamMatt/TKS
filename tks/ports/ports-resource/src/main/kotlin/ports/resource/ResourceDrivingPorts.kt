package ports.resource

import domain.common.exceptions.ResourceNotFoundException
import domain.common.exceptions.UnknownResourceException
import domain.common.valueobjects.AccessionNumber
import domain.resource.Resource

interface ResourceQueryPort {
    @Throws(ResourceNotFoundException::class)
    fun getDetails(accessionNumber: AccessionNumber): Resource?
}

interface ResourceManageCommandPort {
    @Throws(UnknownResourceException::class)
    fun create(resource: Resource)
    fun update(resource: Resource)
    fun remove(accessionNumber: AccessionNumber)
}

interface IResourceService : ResourceQueryPort, ResourceManageCommandPort
