package microservices.user.management.webservices

import javax.jws.WebService


@WebService(endpointInterface = "microservices.user.management.webservices.UserService", serviceName = "UserServiceService", portName = "UserServicePort", targetNamespace = "https://ftims.edu.p.lodz.pl")
//@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
open class UserServiceImpl : UserService{


    override fun reply(msg: String): String {
        return "Thanks for $msg"
    }


}