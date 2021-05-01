package domain.exceptions

import domain.model.ErrorCode

class UnknownResourceException : DomainException(ErrorCode.InvalidResourceFormat)