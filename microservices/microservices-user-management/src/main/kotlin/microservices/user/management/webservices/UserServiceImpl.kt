package microservices.user.management.webservices

import core.domain.common.exceptions.UserNotFoundException
import microservices.user.management.adapters.UserServiceAdapter
import microservices.user.management.dto.UserDto
import javax.inject.Inject
import javax.jws.WebService


@WebService(endpointInterface = "microservices.user.management.webservices.UserService", serviceName = "UserServiceService", portName = "UserServicePort", targetNamespace = "https://ftims.edu.p.lodz.pl")
//@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
open class UserServiceImpl : UserService{

    @Inject
    private lateinit var adapter: UserServiceAdapter


    override fun getUser(email: String): UserDto {
        return adapter.queryUser(email)
    }

    override fun registerUser(model: UserDto): UserDto {
        return adapter.registerUser(model)
    }

    @Throws(UserNotFoundException::class)
    override fun changeUserState(email: String, state: Boolean){
        return adapter.changeState(email, state);
    }

    @Throws(UserNotFoundException::class)
    override fun changeUserRole(email: String, role: String){
        return adapter.changeRole(email, role)
    }


}