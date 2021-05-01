package domain.common.exceptions

import domain.common.ErrorCode

class DuplicatedEmailException : DomainException(ErrorCode.DuplicatedEmail)