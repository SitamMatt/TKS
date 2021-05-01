package domain.common.exceptions

import domain.common.ErrorCode

class InvalidUserException : DomainException(ErrorCode.RentDoesNotExist)