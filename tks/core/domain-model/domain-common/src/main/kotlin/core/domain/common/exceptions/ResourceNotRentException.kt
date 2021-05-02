package core.domain.common.exceptions

import core.domain.common.ErrorCode

class ResourceNotRentException : core.domain.common.exceptions.DomainException(core.domain.common.ErrorCode.RentDoesNotExist)