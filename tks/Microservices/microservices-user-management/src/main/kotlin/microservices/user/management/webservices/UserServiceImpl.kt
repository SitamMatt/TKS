package microservices.user.management.webservices

import microservices.user.management.adapters.UserServiceAdapter
import microservices.user.management.dto.UserDto
import ports.user.IUserRepositoryAdapter
import javax.inject.Inject
import javax.jws.WebService


@WebService(endpointInterface = "microservices.user.management.webservices.UserService", serviceName = "UserServiceService", portName = "UserServicePort", targetNamespace = "https://ftims.edu.p.lodz.pl")
//@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
open class UserServiceImpl : UserService{

    @Inject
    private lateinit var adapter: UserServiceAdapter



    override fun reply(msg: String): UserDto {
        val user = adapter.queryUser(msg)
        return user
    }


}