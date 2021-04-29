package domain.common.exceptions

import domain.common.ErrorCode

class ResourceNotRentException : DomainException(ErrorCode.RentDoesNotExist)