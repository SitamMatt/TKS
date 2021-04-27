package application.services

import application.helpers.AccessionNumberHelper
import domain.exceptions.IncompatibleResourceFormatException
import domain.exceptions.ResourceBlockedByRentException
import domain.exceptions.ResourceNotFoundException
import domain.exceptions.UnknownResourceException
import domain.model.context.library.Book
import domain.model.context.library.Magazine
import domain.model.context.library.Resource
import domain.model.values.AccessionNumber
import ports.primary.combined.IResourceService
import ports.secondary.ResourcePersistencePort
import ports.secondary.ResourceSearchPort

open class ResourcesService(
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