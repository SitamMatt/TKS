package domain.common.exceptions

import domain.common.ErrorCode

class UserNotActiveException : DomainException(ErrorCode.InactiveUser)