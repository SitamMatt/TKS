package webservice.webservices

import client.TypeValidationFailedException_Exception
import client.UserNotFoundException_Exception
import client.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.URL

class UserWebServiceTest {

    private lateinit var service: UserService

    @BeforeEach
    fun init() {
        val url = URL("http://localhost:8080/tks-soap/UserService?wsdl")
        service = UserService(url)
    }


    @Test
    fun `Given valid email, service should return appropiate User`() {
        val port = service.userPort
        val result = port.getUser("mszewc@edu.pl")
        assertEquals("mszewc@edu.pl", result.email)
        assertEquals(true, result.isActive)
        assertEquals("ADMIN", result.role)
    }

    @Test
    fun `Given nonexistent email service should throw`() {
        val port = service.userPort
        assertThrows<UserNotFoundException_Exception> {
            port.getUser("mzab@op.pl")
        }
    }

    @Test
    fun `Given invalid email, service should throw`() {
        val port = service.userPort
        assertThrows<TypeValidationFailedException_Exception> {
            port.getUser("aaa")
        }
    }
}