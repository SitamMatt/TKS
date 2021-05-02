package core.domain.common.exceptions

import core.domain.common.ErrorCode

class ResourceAlreadyRentException : DomainException(ErrorCode.ResourceRented)