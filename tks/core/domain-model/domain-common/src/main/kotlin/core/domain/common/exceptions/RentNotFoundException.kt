package core.domain.common.exceptions

import core.domain.common.ErrorCode

class RentNotFoundException : DomainException(ErrorCode.RentDoesNotExist)