package core.domain.common.exceptions

import core.domain.common.ErrorCode

class DuplicatedEmailException : DomainException(ErrorCode.DuplicatedEmail)