package core.domain.common.exceptions

import core.domain.common.ErrorCode

class ResourceNotRentException : DomainException(ErrorCode.RentDoesNotExist)