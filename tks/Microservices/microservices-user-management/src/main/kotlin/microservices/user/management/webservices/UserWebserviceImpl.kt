package microservices.user.management.webservices

import javax.jws.WebParam
import javax.jws.WebService
import javax.xml.ws.BindingType
import javax.xml.ws.soap.SOAPBinding


@WebService(endpointInterface = "microservices.user.management.webservices.UserWebservice", serviceName = "UserWebservice")
//@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
open class UserWebserviceImpl : UserWebservice{


    override fun reply(msg: String): String {
        return "Thanks for $msg"
    }


}