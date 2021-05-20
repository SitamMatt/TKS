package microservices.user.management.webservices

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import webservices.client.UserServiceService
import java.net.URL

internal class UserServiceImplTest{


    @Test
    fun test(){
        val service  = UserServiceService(URL("http://localhost:8080/soap/userservice?wsdl"))
        val port = service.userServicePort
        val response = port.reply("mszewc@edu.pl")
        println(response)
    }
}