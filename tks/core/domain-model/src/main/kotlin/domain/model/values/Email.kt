package domain.model.values

import domain.exceptions.TypeValidationFailedException
import org.apache.commons.validator.routines.EmailValidator

@JvmInline
value class Email(val value: String) {
    init {
        if (!EmailValidator.getInstance(false).isValid(value)) throw TypeValidationFailedException()
    }
}