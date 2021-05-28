package microservices.user.management.webservices

import microservices.user.management.adapters.UserServiceAdapter
import microservices.user.management.dto.UserDto
import javax.inject.Inject
import javax.jws.WebService


@WebService(endpointInterface = "microservices.user.management.webservices.UserService", serviceName = "UserServiceService", portName = "UserServicePort", targetNamespace = "https://ftims.edu.p.lodz.pl")
//@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
open class UserServiceImpl : UserService{

    @Inject
    private lateinit var adapter: UserServiceAdapter


    override fun reply(msg: String): UserDto {
        return adapter.queryUser(msg)
    }

    override fun registerUser(model: UserDto): UserDto {
        return adapter.registerUser(model)
    }


}