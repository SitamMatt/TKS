package core.domain.common.helpers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class AccessionNumberHelperTest {
    @Test
    fun validityTest() {
        Assertions.assertDoesNotThrow(core.domain.common.helpers.AccessionNumberHelper::generate)
    }
}