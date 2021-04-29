package domain.common.exceptions

import domain.common.ErrorCode

class RentNotFoundException : DomainException(ErrorCode.RentDoesNotExist)