package webservice.mappers

import domain.model.Rent
import domain.model.values.AccessionNumber
import domain.model.values.Email
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class RentMapperTest{

    private lateinit var mapper: RentMapper

    @BeforeEach
    fun init(){
        mapper = RentMapper.INSTANCE
    }

    @Test
    fun `to DTO`(){
        val rent = Rent(UUID.randomUUID(), Date(), null, Email("mszewc@edu.pl"), AccessionNumber("EEEE-254"))
        val dto = mapper.toDto(rent)
        assertNotNull(dto!!)
        assertEquals("EEEE-254", dto.resourceAccessionNumber)
        assertEquals("mszewc@edu.pl", dto.userEmail)
    }

    @Test
    fun `nullable to DTO`(){
        val dto = mapper.toDto(null)
        assertNull(dto)
    }
}