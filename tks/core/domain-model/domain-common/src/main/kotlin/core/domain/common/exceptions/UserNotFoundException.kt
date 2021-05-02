package core.domain.common.exceptions

import core.domain.common.ErrorCode

class UserNotFoundException : DomainException(ErrorCode.UserNotFound)