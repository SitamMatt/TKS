package webservices

import jakarta.jws.WebMethod
import jakarta.jws.WebService

@WebService(name = "UserWebservice", targetNamespace = "https://ftims.edu.p.lodz.pl")
interface UserWebservice {

    @WebMethod
    fun find(id: String) : UserDto
}

