package ports.resource

import domain.exceptions.ResourceNotFoundException
import domain.exceptions.UnknownResourceException
import domain.model.context.library.Resource
import domain.model.values.AccessionNumber

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
