package domain.exceptions

import domain.model.ErrorCode

open class DomainException(val error: ErrorCode) : Exception()