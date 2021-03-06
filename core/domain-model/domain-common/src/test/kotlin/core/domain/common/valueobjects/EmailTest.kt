package core.domain.common.valueobjects

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class EmailTest {

    @Test
    fun equalityTest() {
        val email1 = Email("mszewc@edu.pl")
        val email2 = Email("mszewc@edu.pl")
        val email3 = Email("mzab@edu.pl")
        Assertions.assertNotSame(email1, email2)
        Assertions.assertEquals(email1, email2)
        Assertions.assertNotEquals(email1, email3)
    }

    @Test
    fun constructorValidationTest() {
        Assertions.assertDoesNotThrow<Email> { Email("mszewc@edu.pl") }
        Assertions.assertThrows(core.domain.common.exceptions.TypeValidationFailedException::class.java) { Email("mszewc") }
        Assertions.assertThrows(core.domain.common.exceptions.TypeValidationFailedException::class.java) { Email("mszewc@edu.pl@hehe") }
        Assertions.assertThrows(core.domain.common.exceptions.TypeValidationFailedException::class.java) { Email("") }
    }
}