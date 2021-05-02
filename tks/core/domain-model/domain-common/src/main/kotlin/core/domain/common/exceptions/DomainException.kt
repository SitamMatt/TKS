package core.domain.common.exceptions

import core.domain.common.ErrorCode


open class DomainException(val error: core.domain.common.ErrorCode) : Exception()