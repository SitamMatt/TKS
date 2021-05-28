package core.domain.common.exceptions

import core.domain.common.ErrorCode

class InvalidUserException : DomainException(ErrorCode.RentDoesNotExist)