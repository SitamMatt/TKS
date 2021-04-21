package webservice.webservices

import com.sun.xml.ws.runtime.config.ObjectFactory
import domain.exceptions.TypeValidationFailedException
import domain.exceptions.UserNotFoundException
import webservice.adapters.UserWebServiceAdapter
import webservice.dto.UserSoapDto
import javax.inject.Inject
import javax.jws.WebMethod
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding
import javax.xml.bind.annotation.XmlSeeAlso


@WebService(targetNamespace = "http://superbiz.org/wsdl")
interface IUserWebService {
    @WebMethod
    @Throws(UserNotFoundException::class, TypeValidationFailedException::class)
    fun getUser(email: String): UserSoapDto
}

@WebService(
    portName = "UserPort",
    serviceName = "UserService",
    targetNamespace = "http://superbiz.org/wsdl",
    endpointInterface = "webservice.webservices.IUserWebService")
open class UserWebService : IUserWebService {

    @Inject
    private var serviceAdapter: UserWebServiceAdapter? = null

    @WebMethod
    @Throws(UserNotFoundException::class, TypeValidationFailedException::class)
    override fun getUser(email: String): UserSoapDto {
        try {
            var res = serviceAdapter!!.getUser(email)
            return res
        } catch (e: UserNotFoundException) {
            throw e
        } catch (e: TypeValidationFailedException) {
            throw e
        }
    }
}