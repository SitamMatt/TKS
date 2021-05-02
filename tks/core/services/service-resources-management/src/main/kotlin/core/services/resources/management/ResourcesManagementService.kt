package core.services.resources.management

import core.domain.common.exceptions.IncompatibleResourceFormatException
import core.domain.common.exceptions.ResourceBlockedByRentException
import core.domain.common.exceptions.ResourceNotFoundException
import core.domain.common.exceptions.UnknownResourceException
import core.domain.common.helpers.AccessionNumberHelper
import core.domain.common.valueobjects.AccessionNumber
import core.domain.resource.Book
import core.domain.resource.Magazine
import core.domain.resource.Resource
import ports.resource.IResourceService
import ports.resource.ResourcePersistencePort
import ports.resource.ResourceSearchPort

open class ResourcesManagementService(
    private val resourcePersistencePort: ResourcePersistencePort,
    private val resourceSearchPort: ResourceSearchPort,
) : IResourceService {


    @Throws(core.domain.common.exceptions.UnknownResourceException::class)
    override fun create(resource: Resource) {
        if (resource.accessionNumber != null) throw core.domain.common.exceptions.UnknownResourceException()
        when (resource) {
            is Book, is Magazine -> {
                resource.accessionNumber = core.domain.common.helpers.AccessionNumberHelper.generate()
                resourcePersistencePort.save(resource)
            }
            else -> throw core.domain.common.exceptions.UnknownResourceException()
        }
    }

    override fun update(resource: Resource) {
        if (resource.accessionNumber == null) throw core.domain.common.exceptions.ResourceNotFoundException()
        val original =
            resourceSearchPort.findByAccessionNumber(resource.accessionNumber!!) ?: throw core.domain.common.exceptions.ResourceNotFoundException()
        if (resource::class != original::class) throw core.domain.common.exceptions.IncompatibleResourceFormatException()
        resourcePersistencePort.save(resource)
    }

    override fun remove(accessionNumber: core.domain.common.valueobjects.AccessionNumber) {
        val resource = resourceSearchPort.findByAccessionNumber(accessionNumber) ?: throw core.domain.common.exceptions.ResourceNotFoundException()
        if (resource.locked) throw core.domain.common.exceptions.ResourceBlockedByRentException()
        resourcePersistencePort.remove(resource)
    }

    @Throws(core.domain.common.exceptions.ResourceNotFoundException::class)
    override fun getDetails(accessionNumber: core.domain.common.valueobjects.AccessionNumber): Resource {
        return resourceSearchPort.findByAccessionNumber(accessionNumber) ?: throw core.domain.common.exceptions.ResourceNotFoundException()
    }
}