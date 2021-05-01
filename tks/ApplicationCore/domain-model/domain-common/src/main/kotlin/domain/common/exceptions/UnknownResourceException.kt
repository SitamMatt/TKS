package domain.common.exceptions

import domain.common.ErrorCode

class UnknownResourceException : DomainException(ErrorCode.InvalidResourceFormat)