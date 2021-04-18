package application.helpers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.ThrowingSupplier

internal class AccessionNumberHelperTest {
    @Test
    fun validityTest() {
        Assertions.assertDoesNotThrow(AccessionNumberHelper::generate)
    }
}