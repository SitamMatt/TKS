package core.domain.common.exceptions

import core.domain.common.ErrorCode

class UnknownResourceException : core.domain.common.exceptions.DomainException(core.domain.common.ErrorCode.InvalidResourceFormat)