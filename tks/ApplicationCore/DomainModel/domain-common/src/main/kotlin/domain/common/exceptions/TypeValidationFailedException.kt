package domain.common.exceptions

import domain.common.ErrorCode

class TypeValidationFailedException : DomainException(ErrorCode.InvalidIdentifierFormat)