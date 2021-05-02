@file:Suppress("SpellCheckingInspection")

package core.domain.common.valueobjects

import core.domain.common.exceptions.TypeValidationFailedException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class AccessionNumberTest {
    @Test
    fun validationTest() {
        Assertions.assertThrows(core.domain.common.exceptions.TypeValidationFailedException::class.java) {
            core.domain.common.valueobjects.AccessionNumber(
                "xxx"
            )
        }
        Assertions.assertThrows(core.domain.common.exceptions.TypeValidationFailedException::class.java) {
            core.domain.common.valueobjects.AccessionNumber(
                "AAAA-5555"
            )
        }
        Assertions.assertThrows(core.domain.common.exceptions.TypeValidationFailedException::class.java) {
            core.domain.common.valueobjects.AccessionNumber(
                "AAAA-55"
            )
        }
        Assertions.assertDoesNotThrow<core.domain.common.valueobjects.AccessionNumber> {
            core.domain.common.valueobjects.AccessionNumber(
                "SCXI-591"
            )
        }
    }

    @Test
    fun equalityTest() {
        val accessionNumber1 = core.domain.common.valueobjects.AccessionNumber("SCXI-591")
        val accessionNumber2 = core.domain.common.valueobjects.AccessionNumber("SCXI-591")
        val accessionNumber3 = core.domain.common.valueobjects.AccessionNumber("SCXI-592")
        Assertions.assertNotSame(accessionNumber1, accessionNumber2)
        Assertions.assertEquals(accessionNumber1, accessionNumber2)
        Assertions.assertNotEquals(accessionNumber1, accessionNumber3)
    }
}