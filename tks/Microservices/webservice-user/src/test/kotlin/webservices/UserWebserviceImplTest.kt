package webservices

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import webservices.client.ObjectFactory
import webservices.client.UserWebservice
import webservices.client.UserWebserviceImplService
import java.net.URL

internal class UserWebserviceImplTest{


    @Test
    fun `test`(){
        val service = UserWebserviceImplService(URL(""))
        val port = service.userWebserviceImplPort
//        port.find()
    }
}