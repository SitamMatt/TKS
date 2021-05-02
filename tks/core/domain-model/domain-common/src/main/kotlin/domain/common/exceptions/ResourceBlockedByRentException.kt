package domain.common.exceptions

import domain.common.ErrorCode

class ResourceBlockedByRentException : DomainException(ErrorCode.ResourceInUse)