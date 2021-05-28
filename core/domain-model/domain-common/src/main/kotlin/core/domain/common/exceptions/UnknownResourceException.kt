package core.domain.common.exceptions

import core.domain.common.ErrorCode

class UnknownResourceException : DomainException(ErrorCode.InvalidResourceFormat)