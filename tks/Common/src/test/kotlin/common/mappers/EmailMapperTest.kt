package common.mappers

import domain.exceptions.TypeValidationFailedException
import domain.model.values.Email
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mapstruct.factory.Mappers

class EmailMapperTest {

    @Test
    fun toDomainObjectTest() {
        val mapper = Mappers.getMapper(EmailMapper::class.java)
        val source = "matzab@edu.pl"
        val email = mapper.toEmail(source)
        Assertions.assertNotNull(email)
        Assertions.assertEquals(source, email?.value)
    }

    @Test
    fun toDomainObjectNullableTest() {
        val mapper = Mappers.getMapper(EmailMapper::class.java)
        var email: Email? = Email("matzab@edu.pl")
        Assertions.assertDoesNotThrow {
            email = mapper.toEmail(null)
        }
        Assertions.assertNull(email)
    }

    @Test
    fun toStringTest() {
        val mapper = Mappers.getMapper(EmailMapper::class.java)
        val email = Email("matzab@edu.pl")
        val emailStr = mapper.toString(email)
        Assertions.assertNotNull(emailStr)
        Assertions.assertEquals("matzab@edu.pl", emailStr)
    }

    @Test
    fun toStringNullableTest() {
        val mapper = Mappers.getMapper(EmailMapper::class.java)
        var emailStr: String? = ""
        Assertions.assertDoesNotThrow {
            emailStr = mapper.toString(null)
        }
        Assertions.assertNull(emailStr)
    }

    @Test
    fun validationErrorTest() {
        val mapper = Mappers.getMapper(EmailMapper::class.java)
        val source = "matt.edu.pl"
        assertThrows<TypeValidationFailedException> { mapper.toEmail(source) }
    }
}