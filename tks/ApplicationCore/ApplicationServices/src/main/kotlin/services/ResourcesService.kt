package services

import exceptions.IncompatibleResourceFormatException
import exceptions.ResourceBlockedByRentException
import exceptions.ResourceNotFoundException
import exceptions.UnknownResourceException
import drivenports.RentQueryPort
import helpers.AccessionNumberHelper
import ports.secondary.ResourcePersistencePort
import ports.secondary.ResourceSearchPort
import model.Book
import model.Magazine
import model.Resource
import model.values.AccessionNumber
import ports.primary.IResourceService

class ResourcesService(
    private val resourcePersistencePort: ResourcePersistencePort,
    private val resourceSearchPort: ResourceSearchPort,
    private val rentQueryPort: RentQueryPort
) : IResourceService{


    @Throws(UnknownResourceException::class)
    override fun create(resource: Resource) {
        if(resource.accessionNumber != null) throw UnknownResourceException()
        when(resource){
            is Book, is Magazine -> {
                resource.accessionNumber = AccessionNumberHelper.generate()
                resourcePersistencePort.add(resource)
            }
            else -> throw UnknownResourceException()
        }
    }

    override fun update(resource: Resource){
        val original = resourceSearchPort.findById(resource.accessionNumber) ?: throw ResourceNotFoundException()
        if(resource::class != original::class) throw IncompatibleResourceFormatException()
        resourcePersistencePort.save(resource)
    }

    override fun remove(accessionNumber: AccessionNumber){
        val resource = resourceSearchPort.findById(accessionNumber) ?: throw ResourceNotFoundException()
        val rent = rentQueryPort.findActiveByResourceId(accessionNumber);
        if(rent != null) throw ResourceBlockedByRentException()
        resourcePersistencePort.remove(resource)
    }

    @Throws(ResourceNotFoundException::class)
    override fun getDetails(accessionNumber: AccessionNumber): Resource {
        return resourceSearchPort.findById(accessionNumber) ?: throw ResourceNotFoundException();
    }
}