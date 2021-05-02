package core.domain.common.exceptions

import core.domain.common.ErrorCode

class UserNotActiveException : DomainException(ErrorCode.InactiveUser)