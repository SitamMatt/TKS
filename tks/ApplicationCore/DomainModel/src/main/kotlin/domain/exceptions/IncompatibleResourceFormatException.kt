package domain.exceptions

import domain.model.ErrorCode

class IncompatibleResourceFormatException : DomainException(ErrorCode.InvalidResourceFormat)