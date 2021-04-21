package domain.exceptions

import domain.model.ErrorCode

class InvalidUserException : DomainException(ErrorCode.RentDoesNotExist)