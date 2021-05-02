package ports.resource

import core.domain.common.exceptions.ResourceNotFoundException
import core.domain.common.exceptions.UnknownResourceException
import core.domain.common.valueobjects.AccessionNumber
import core.domain.resource.Resource

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
