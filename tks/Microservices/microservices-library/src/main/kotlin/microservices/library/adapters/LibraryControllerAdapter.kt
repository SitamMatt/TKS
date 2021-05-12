package microservices.library.adapters

import core.domain.common.valueobjects.AccessionNumber
import core.domain.resource.Resource
import microservices.common.error.ResourceNotFoundException
import microservices.library.dto.LibraryResourceDto
import microservices.library.mappers.toDomain
import microservices.library.mappers.toDto
import ports.resource.IResourceService
import javax.inject.Inject

class LibraryControllerAdapter {

    @Inject
    lateinit var service: IResourceService

    fun query(id: String): Result<LibraryResourceDto> {
        val accessionNumber = AccessionNumber(id)
        val resource = service.getDetails(accessionNumber)
            ?: return Result.failure(ResourceNotFoundException())
        return Result.success(resource.toDto())
    }

    fun add(model: LibraryResourceDto): Result<Resource> {
        return try {
            val resource = model.toDomain()
            val result = service.create(resource)
            Result.success(result)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    fun update(id: String, model: LibraryResourceDto): Result<Resource> {
        return try {
            model.accessionNumber = id
            val resource = model.toDomain()
            val result = service.update(resource)
            Result.success(result)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    fun remove(id: String): Result<Boolean> {
        return try {
            val accessionNumber = AccessionNumber(id)
            service.remove(accessionNumber)
            return Result.success(true)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}