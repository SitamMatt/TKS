package microservices.user.management.webservices

import core.domain.common.exceptions.UserNotFoundException
import microservices.user.management.dto.UserDto
import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebService

@WebService(targetNamespace = "https://ftims.edu.p.lodz.pl")
interface UserService {

    @WebMethod
    fun getUser(@WebParam(name = "email") email: String): UserDto

    @WebMethod
    fun registerUser(@WebParam(name = "model") model: UserDto): UserDto

    @Throws(UserNotFoundException::class)
    @WebMethod
    fun changeUserState(@WebParam(name = "email") email: String, @WebParam(name = "state") state: Boolean)

    @Throws(UserNotFoundException::class)
    @WebMethod
    fun changeUserRole(@WebParam(name = "email") email: String, @WebParam(name = "role") role: String)
}