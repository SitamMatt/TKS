package webservice.webservices

import domain.exceptions.ResourceNotFoundException
import domain.exceptions.TypeValidationFailedException
import webservice.adapters.LibraryItemWebServiceAdapter
import webservice.dto.LibraryItemSoapDto
import javax.inject.Inject
import javax.jws.WebMethod
import javax.jws.WebService

@WebService(targetNamespace = "http://superbiz.org/wsdl")
interface ILibraryItemWebService {
    @WebMethod
    @Throws(TypeValidationFailedException::class, ResourceNotFoundException::class)
    fun getLibraryItem(accessionNumber: String): LibraryItemSoapDto
}

@WebService(
    portName = "LibraryItemPort",
    serviceName = "LibraryItemService",
    targetNamespace = "http://superbiz.org/wsdl",
    endpointInterface = "webservice.webservices.ILibraryItemWebService")
open class LibraryItemWebService : ILibraryItemWebService {
    @Inject
    private var serviceAdapter: LibraryItemWebServiceAdapter? = null
    @WebMethod
    @Throws(TypeValidationFailedException::class, ResourceNotFoundException::class)
    override fun getLibraryItem(accessionNumber: String): LibraryItemSoapDto {
        return try {
            serviceAdapter!!.getLibraryItem(accessionNumber)
        } catch (e: TypeValidationFailedException) {
            throw e
        } catch (e: ResourceNotFoundException) {
            throw e
        }
    }
}