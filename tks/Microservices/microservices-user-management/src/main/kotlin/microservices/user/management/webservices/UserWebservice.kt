package microservices.user.management.webservices

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebService

@WebService
interface UserWebservice {

    @WebMethod
    fun reply(@WebParam(name = "msg") msg: String): String
}