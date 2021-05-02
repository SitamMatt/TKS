package domain.common.exceptions

import domain.common.ErrorCode

class ResourceAlreadyRentException : DomainException(ErrorCode.ResourceRented)