package core.domain.common.exceptions

import core.domain.common.ErrorCode

class ResourceAlreadyRentException : core.domain.common.exceptions.DomainException(core.domain.common.ErrorCode.ResourceRented)