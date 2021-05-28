package core.domain.common.valueobjects

import core.domain.common.exceptions.TypeValidationFailedException

@JvmInline
value class AccessionNumber(val value: String) {
    companion object {
        val format = Regex("\\b[A-Z]{4}-[0-9]{3}\\b")
    }

    init {
        if (!value.matches(format)) throw TypeValidationFailedException()
    }
}