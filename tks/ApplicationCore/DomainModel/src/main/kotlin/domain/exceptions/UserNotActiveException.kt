package domain.exceptions

import domain.model.ErrorCode

class UserNotActiveException : DomainException(ErrorCode.InactiveUser)