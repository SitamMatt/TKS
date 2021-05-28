package core.domain.common.exceptions

import core.domain.common.ErrorCode

class TypeValidationFailedException : DomainException(ErrorCode.InvalidIdentifierFormat)