package microservices.user.management.webservices

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import webservices.client.UserDto
import webservices.client.UserServiceService
import java.net.URL

internal class UserServiceImplTest{


    @Test
    fun queryTest(){
        val service  = UserServiceService(URL("http://localhost:8080/soap/userservice?wsdl"))
        val port = service.userServicePort
        val response = port.reply("mszewc@edu.pl")
        println(response)
    }

    @Test
    fun createTest(){
        val service  = UserServiceService(URL("http://localhost:8080/soap/userservice?wsdl"))
        val port = service.userServicePort
        val dto = UserDto()
        dto.email = "mzab@edu.pl"
        dto.isActive = true
        dto.password = "####"
        dto.role = "ADMIN"
        val response = port.registerUser(dto)
        println(response)
    }
}