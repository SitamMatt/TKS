package services

import exceptions.IncompatibleResourceFormatException
import exceptions.ResourceNotFoundException
import exceptions.UnknownResourceException
import interfaces.ResourceManagePort
import interfaces.ResourceQueryPort
import model.Book
import model.Magazine
import model.Resource
import java.util.*

class ResourcesService(
    private val resourceManagePort: ResourceManagePort,
    private val resourceQueryPort: ResourceQueryPort
) {


    fun create(resource: Resource) {
        if(resource.id != null) throw UnknownResourceException()
        when(resource){
            is Book, is Magazine -> {
                resource.id = UUID.randomUUID()
                resourceManagePort.add(resource)
            }
            else -> throw UnknownResourceException()
        }
    }

    fun update(resource: Resource){
        val original = resourceQueryPort.findById(resource.id) ?: throw ResourceNotFoundException()
        if(resource::class != original::class) throw IncompatibleResourceFormatException()
        resourceManagePort.save(resource)
    }

    fun remove(resourceId: UUID){
        val resource = resourceQueryPort.findById(resourceId) ?: throw ResourceNotFoundException()
        // todo check existing rent
        resourceManagePort.remove(resource)
    }

    fun getDetails(resourceId: UUID): Resource {
        return resourceQueryPort.findById(resourceId) ?: throw ResourceNotFoundException();
    }
}