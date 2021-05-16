package domain.exceptions

import domain.model.ErrorCode

class ResourceAlreadyRentException : DomainException(ErrorCode.ResourceRented)