package webservice.mappers

import domain.model.Book
import domain.model.traits.Resource
import domain.model.values.AccessionNumber
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ResourceMapperTest{

    private lateinit var mapper: ResourceMapper

    class InvalidResource(override var accessionNumber: AccessionNumber?, override var title: String) : Resource

    @BeforeEach
    fun init(){
        mapper = ResourceMapper.INSTANCE
    }

    @Test
    fun `to DTO`(){
        val book = Book(AccessionNumber("EEEE-254"), "Diuna", "Frank Herbert")
        val dto = mapper.toDto(book)
        assertNotNull(dto!!)
        assertEquals("EEEE-254", dto.accessionNumber)
        assertEquals("Diuna", dto.title)
        assertEquals("Frank Herbert", dto.author)
        assertEquals("BOOK", dto.type)
        assertNull(dto.publisher)
    }

    @Test
    fun `nullable to DTO`(){
        val dto = mapper.toDto(null)
        assertNull(dto)
    }

    @Test
    fun `unknown type to DTO`(){
        val dto = mapper.toDto(InvalidResource(AccessionNumber("EEEE-254"), "abc"))
        assertNull(dto)
    }
}