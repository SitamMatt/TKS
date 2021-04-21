package webservice.webservices

import client.TypeValidationFailedException_Exception
import client.UserNotFoundException_Exception
import client.UserService
import client.rent.RentNotFoundException_Exception
import client.rent.RentService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.URL

class RentWebServiceTest{

    private lateinit var service: RentService

    @BeforeEach
    fun init() {
        val url = URL("http://localhost:8080/tks-soap/RentService?wsdl")
        service = RentService(url)
    }


    @Test
    fun `Given valid email, service should return appropiate User`() {
        val port = service.rentPort
        val result = port.getRent("8245c01f-0a67-4f7a-b184-4af7534bb930")
        assertEquals("mszewc@edu.pl", result.userEmail)
        assertEquals("EEEE-254", result.resourceAccessionNumber)
        assertEquals("8245c01f-0a67-4f7a-b184-4af7534bb930", result.id)
    }

    @Test
    fun `Given nonexistent email service should throw`() {
        val port = service.rentPort
        org.junit.jupiter.api.assertThrows<RentNotFoundException_Exception> {
            port.getRent("8225c01f-0a67-4f7a-b184-4af7534bb930")
        }
    }
}