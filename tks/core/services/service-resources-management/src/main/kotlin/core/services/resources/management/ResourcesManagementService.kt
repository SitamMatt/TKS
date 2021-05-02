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


    @Throws(UnknownResourceException::class)
    override fun create(resource: Resource) {
        if (resource.accessionNumber != null) throw UnknownResourceException()
        when (resource) {
            is Book, is Magazine -> {
                resource.accessionNumber = AccessionNumberHelper.generate()
                resourcePersistencePort.save(resource)
            }
            else -> throw UnknownResourceException()
        }
    }

    override fun update(resource: Resource) {
        if (resource.accessionNumber == null) throw ResourceNotFoundException()
        val original =
            resourceSearchPort.findByAccessionNumber(resource.accessionNumber!!) ?: throw ResourceNotFoundException()
        if (resource::class != original::class) throw IncompatibleResourceFormatException()
        resourcePersistencePort.save(resource)
    }

    override fun remove(accessionNumber: AccessionNumber) {
        val resource = resourceSearchPort.findByAccessionNumber(accessionNumber) ?: throw ResourceNotFoundException()
        if (resource.locked) throw ResourceBlockedByRentException()
        resourcePersistencePort.remove(resource)
    }

    @Throws(ResourceNotFoundException::class)
    override fun getDetails(accessionNumber: AccessionNumber): Resource {
        return resourceSearchPort.findByAccessionNumber(accessionNumber) ?: throw ResourceNotFoundException()
    }
}