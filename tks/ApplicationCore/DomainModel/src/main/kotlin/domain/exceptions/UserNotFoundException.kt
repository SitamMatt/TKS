package domain.exceptions

import domain.exceptions.DomainException
import domain.model.ErrorCode

class UserNotFoundException : DomainException(ErrorCode.UserNotFound)