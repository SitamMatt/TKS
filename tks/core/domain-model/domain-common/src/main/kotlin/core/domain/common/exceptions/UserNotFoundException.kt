package core.domain.common.exceptions

import core.domain.common.ErrorCode

class UserNotFoundException : core.domain.common.exceptions.DomainException(core.domain.common.ErrorCode.UserNotFound)