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
import java.util.*

class ResourcesService(
    private val resourcePersistencePort: ResourcePersistencePort,
    private val resourceSearchPort: ResourceSearchPort,
    private val rentQueryPort: RentQueryPort
) : IResourceService{


    override fun create(resource: Resource) {
        if(resource.id != null) throw UnknownResourceException()
        when(resource){
            is Book, is Magazine -> {
                resource.id = AccessionNumberHelper.generate()
                resourcePersistencePort.add(resource)
            }
            else -> throw UnknownResourceException()
        }
    }

    override fun update(resource: Resource){
        val original = resourceSearchPort.findById(resource.id) ?: throw ResourceNotFoundException()
        if(resource::class != original::class) throw IncompatibleResourceFormatException()
        resourcePersistencePort.save(resource)
    }

    override fun remove(resourceId: AccessionNumber){
        val resource = resourceSearchPort.findById(resourceId) ?: throw ResourceNotFoundException()
        val rent = rentQueryPort.findActiveByResourceId(resourceId);
        if(rent != null) throw ResourceBlockedByRentException()
        resourcePersistencePort.remove(resource)
    }

    override fun getDetails(resourceId: AccessionNumber): Resource {
        return resourceSearchPort.findById(resourceId) ?: throw ResourceNotFoundException();
    }
}