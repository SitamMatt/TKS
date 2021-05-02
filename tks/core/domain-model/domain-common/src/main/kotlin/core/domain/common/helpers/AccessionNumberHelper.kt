package core.domain.common.helpers

import core.domain.common.valueobjects.AccessionNumber
import java.util.*

object AccessionNumberHelper {

    @JvmStatic
    fun generate(): core.domain.common.valueobjects.AccessionNumber {
        val leftLimit = 65 // letter 'A'
        val rightLimit = 90 // letter 'Z'
        val targetStringLength = 8
        val random = Random()
        val buffer = StringBuilder(targetStringLength)
        for (i in 0..3) {
            val randomLimitedInt = leftLimit + (random.nextFloat() * (rightLimit - leftLimit + 1)).toInt()
            buffer.append(randomLimitedInt.toChar())
        }
        buffer.append('-')
        buffer.append(random.nextInt(900) + 100)
        val generatedString = buffer.toString()
        return core.domain.common.valueobjects.AccessionNumber(generatedString)
    }
}