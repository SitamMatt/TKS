package core.domain.common.exceptions

import core.domain.common.ErrorCode

class ResourceBlockedByRentException : core.domain.common.exceptions.DomainException(core.domain.common.ErrorCode.ResourceInUse)