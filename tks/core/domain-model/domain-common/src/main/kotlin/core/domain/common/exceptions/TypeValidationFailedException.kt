package core.domain.common.exceptions

import core.domain.common.ErrorCode

class TypeValidationFailedException : core.domain.common.exceptions.DomainException(core.domain.common.ErrorCode.InvalidIdentifierFormat)