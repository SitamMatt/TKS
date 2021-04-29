package domain.common.exceptions

import domain.common.ErrorCode

class IncompatibleResourceFormatException : DomainException(ErrorCode.InvalidResourceFormat)