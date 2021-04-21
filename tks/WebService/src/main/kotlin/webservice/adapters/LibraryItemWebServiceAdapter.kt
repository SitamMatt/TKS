package webservice.adapters

import domain.exceptions.ResourceNotFoundException
import domain.exceptions.TypeValidationFailedException
import domain.model.values.AccessionNumber
import ports.primary.combined.IResourceService
import webservice.dto.LibraryItemSoapDto
import webservice.mappers.ResourceMapper
import javax.inject.Inject

class LibraryItemWebServiceAdapter(
    @Inject private var resourceService: IResourceService? = null,
    @Inject private var mapper: ResourceMapper? = null
) {

    @Throws(TypeValidationFailedException::class, ResourceNotFoundException::class)
    fun getLibraryItem(accessionNumber: String): LibraryItemSoapDto {
        val key = AccessionNumber(accessionNumber)
        val item = resourceService!!.getDetails(key)!!
        return mapper!!.toDto(item)!!
    }
}