package core.domain.common.exceptions

import core.domain.common.ErrorCode

class IncompatibleResourceFormatException : DomainException(ErrorCode.InvalidResourceFormat)