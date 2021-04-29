package domain.common.exceptions

import domain.common.ErrorCode

class UserNotFoundException : DomainException(ErrorCode.UserNotFound)