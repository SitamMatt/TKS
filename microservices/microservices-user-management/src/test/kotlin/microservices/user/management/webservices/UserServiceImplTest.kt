package microservices.user.management.webservices

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utils.KafkaResource
import utils.PostgresResource
import utils.WithKafka
import webservices.client.UserDto
import webservices.client.UserServiceService
import java.net.URL

@QuarkusTest
@QuarkusTestResource(PostgresResource::class)
@WithKafka
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
        val service  = UserServiceService(URL("http://localhost:9090/soap/userservice?wsdl"))
        val port = service.userServicePort
        val dto = UserDto()
        dto.email = "mzab@edu.pl"
        dto.isActive = true
        dto.password = "####"
        dto.role = "ADMIN"
        val response = port.registerUser(dto)
        println(response)
        Thread.sleep(5000)
    }
}