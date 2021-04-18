package common.mappers

import domain.exceptions.TypeValidationFailedException
import domain.model.values.AccessionNumber
import domain.model.values.Email
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

class AccessionNumberMapperTest {

    @Test
    fun toDomainObjectTest(){
        val mapper = Mappers.getMapper(AccessionNumberMapper::class.java)
        val source = "ABCD-123";
        val accessionNumber = mapper.toAccessionNumber(source)
        assertNotNull(accessionNumber)
        assertEquals(source, accessionNumber?.value)
    }

    @Test
    fun toDomainObjectNullableTest(){
        val mapper = Mappers.getMapper(AccessionNumberMapper::class.java)
        var accessionNumber: AccessionNumber? = AccessionNumber("ABCD-123")
        assertDoesNotThrow {
            accessionNumber = mapper.toAccessionNumber(null)
        }
        assertNull(accessionNumber)
    }

    @Test
    fun toStringTest(){
        val mapper = Mappers.getMapper(AccessionNumberMapper::class.java)
        val accessionNumber = AccessionNumber("ABCD-123")
        val accessionNumberStr = mapper.toString(accessionNumber)
        assertNotNull(accessionNumberStr)
        assertEquals("ABCD-123", accessionNumberStr)
    }

    @Test
    fun toStringNullableTest(){
        val mapper = Mappers.getMapper(AccessionNumberMapper::class.java)
        var accessionNumberStr: String? = ""
        assertDoesNotThrow{
            accessionNumberStr = mapper.toString(null)
        }
        assertNull(accessionNumberStr)
    }

    @Test
    fun validationErrorTest(){
        val mapper = Mappers.getMapper(AccessionNumberMapper::class.java)
        val source ="matt.edu.pl"
        org.junit.jupiter.api.assertThrows<TypeValidationFailedException> { mapper.toAccessionNumber(source) }
    }
}