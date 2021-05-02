package core.domain.common.exceptions

import core.domain.common.ErrorCode

class UserNotActiveException : core.domain.common.exceptions.DomainException(core.domain.common.ErrorCode.InactiveUser)