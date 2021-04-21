package rest.api.adapters

import domain.exceptions.ResourceNotFoundException
import domain.exceptions.TypeValidationFailedException
import domain.exceptions.UnknownResourceException
import domain.model.values.AccessionNumber
import ports.primary.combined.IResourceService
import rest.api.dto.LibraryItemDto
import rest.api.mappers.LibraryItemMapper
import javax.inject.Inject

class LibraryItemResourceAdapter @Inject constructor(
    private val resourceService: IResourceService,
    private val mapper: LibraryItemMapper
) {

    @Throws(UnknownResourceException::class)
    fun add(dto: LibraryItemDto?): String {
        val resource = mapper.toDomainObject(dto!!)
        resourceService.create(resource)
        return resource.accessionNumber!!.value
    }

    @Throws(TypeValidationFailedException::class, ResourceNotFoundException::class)
    fun query(id: String?): LibraryItemDto {
        val accessionNumber = AccessionNumber(id!!)
        val resource = resourceService.getDetails(accessionNumber)
        return mapper.toDto(resource!!)
    }
}