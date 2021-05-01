package domain.common.exceptions

import domain.common.ErrorCode


open class DomainException(val error: ErrorCode) : Exception()