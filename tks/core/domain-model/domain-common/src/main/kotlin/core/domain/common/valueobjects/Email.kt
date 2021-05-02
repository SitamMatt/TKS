package core.domain.common.valueobjects

import core.domain.common.exceptions.TypeValidationFailedException
import org.apache.commons.validator.routines.EmailValidator

@JvmInline
value class Email(val value: String) {
    init {
        if (!EmailValidator.getInstance(false).isValid(value)) throw core.domain.common.exceptions.TypeValidationFailedException()
    }
}