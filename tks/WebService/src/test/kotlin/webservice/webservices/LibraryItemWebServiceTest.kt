package webservice.webservices

import client.libraryitem.LibraryItemService
import client.libraryitem.ResourceNotFoundException_Exception
import client.libraryitem.TypeValidationFailedException_Exception
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.URL

class LibraryItemWebServiceTest{
    private lateinit var service: LibraryItemService

    @BeforeEach
    fun init() {
        val url = URL("http://localhost:8080/tks-soap/LibraryItemService?wsdl")
        service = LibraryItemService(url)
    }


    @Test
    fun `Given valid email, service should return appropiate User`() {
        val port = service.libraryItemPort
        val result = port.getLibraryItem("EEEE-254")
        assertNotNull(result)
        assertEquals("EEEE-254",result.accessionNumber)
        assertEquals("Diuna",result.title)
        assertEquals("Frank Herbert",result.author)
        assertEquals("BOOK",result.type)
        assertNull(result.publisher)
    }

    @Test
    fun `Given nonexistent email service should throw`() {
        val port = service.libraryItemPort
        assertThrows<ResourceNotFoundException_Exception> {
            port.getLibraryItem("AAAA-254")
        }
    }

    @Test
    fun `Given invalid email, service should throw`() {
        val port = service.libraryItemPort
        assertThrows<TypeValidationFailedException_Exception> {
            port.getLibraryItem("aaa")
        }
    }
}