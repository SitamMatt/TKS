package domain.exceptions

import domain.model.ErrorCode

class ResourceNotRentException : DomainException(ErrorCode.RentDoesNotExist)