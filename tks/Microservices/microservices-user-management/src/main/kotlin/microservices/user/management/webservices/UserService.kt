package microservices.user.management.webservices

import microservices.user.management.dto.UserDto
import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebService

@WebService(targetNamespace = "https://ftims.edu.p.lodz.pl")
interface UserService {

    @WebMethod
    fun reply(@WebParam(name = "msg") msg: String): UserDto
}