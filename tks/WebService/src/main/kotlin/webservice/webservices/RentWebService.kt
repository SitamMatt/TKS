package webservice.webservices

import domain.exceptions.RentNotFoundException
import webservice.adapters.RentWebServiceAdapter
import webservice.dto.RentSoapDto
import java.util.*
import javax.inject.Inject
import javax.jws.WebMethod
import javax.jws.WebService

@WebService(targetNamespace = "http://superbiz.org/wsdl")
interface IRentWebService {
    @WebMethod
    @Throws(RentNotFoundException::class)
    fun getRent(id: UUID): RentSoapDto
}

@WebService(
    portName = "RentPort",
    serviceName = "RentService",
    targetNamespace = "http://superbiz.org/wsdl",
    endpointInterface = "webservice.webservices.IRentWebService")
open class RentWebService : IRentWebService {

    @Inject
    private var serviceAdapter: RentWebServiceAdapter? = null

    @WebMethod
    @Throws(RentNotFoundException::class)
    override fun getRent(id: UUID): RentSoapDto {
        try{
            var res = serviceAdapter!!.getRent(id)
            return res;
        }catch (e: RentNotFoundException){
            throw e
        }
    }
}