@file:Suppress("SpellCheckingInspection")

package domain.common.valueobjects

import domain.common.exceptions.TypeValidationFailedException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class AccessionNumberTest {
    @Test
    fun validationTest() {
        Assertions.assertThrows(TypeValidationFailedException::class.java) { AccessionNumber("xxx") }
        Assertions.assertThrows(TypeValidationFailedException::class.java) { AccessionNumber("AAAA-5555") }
        Assertions.assertThrows(TypeValidationFailedException::class.java) { AccessionNumber("AAAA-55") }
        Assertions.assertDoesNotThrow<AccessionNumber> { AccessionNumber("SCXI-591") }
    }

    @Test
    fun equalityTest() {
        val accessionNumber1 = AccessionNumber("SCXI-591")
        val accessionNumber2 = AccessionNumber("SCXI-591")
        val accessionNumber3 = AccessionNumber("SCXI-592")
        Assertions.assertNotSame(accessionNumber1, accessionNumber2)
        Assertions.assertEquals(accessionNumber1, accessionNumber2)
        Assertions.assertNotEquals(accessionNumber1, accessionNumber3)
    }
}