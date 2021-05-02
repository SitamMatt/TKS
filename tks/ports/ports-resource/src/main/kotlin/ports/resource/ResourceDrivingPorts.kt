package ports.resource

import core.domain.common.exceptions.ResourceNotFoundException
import core.domain.common.exceptions.UnknownResourceException
import core.domain.common.valueobjects.AccessionNumber
import core.domain.resource.Resource

interface ResourceQueryPort {
    @Throws(core.domain.common.exceptions.ResourceNotFoundException::class)
    fun getDetails(accessionNumber: core.domain.common.valueobjects.AccessionNumber): Resource?
}

interface ResourceManageCommandPort {
    @Throws(core.domain.common.exceptions.UnknownResourceException::class)
    fun create(resource: Resource)
    fun update(resource: Resource)
    fun remove(accessionNumber: core.domain.common.valueobjects.AccessionNumber)
}

interface IResourceService : ResourceQueryPort, ResourceManageCommandPort
