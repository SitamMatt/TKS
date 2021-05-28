package core.domain.common.exceptions

import core.domain.common.ErrorCode

class ResourceBlockedByRentException : DomainException(ErrorCode.ResourceInUse)