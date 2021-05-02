package core.domain.common.exceptions

import core.domain.common.ErrorCode

class DuplicatedEmailException : core.domain.common.exceptions.DomainException(core.domain.common.ErrorCode.DuplicatedEmail)