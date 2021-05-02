package core.domain.common.exceptions

import core.domain.common.ErrorCode


open class DomainException(val error: ErrorCode) : Exception()