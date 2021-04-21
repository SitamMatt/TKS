package domain.exceptions

import domain.model.ErrorCode

class ResourceBlockedByRentException : DomainException(ErrorCode.ResourceInUse)