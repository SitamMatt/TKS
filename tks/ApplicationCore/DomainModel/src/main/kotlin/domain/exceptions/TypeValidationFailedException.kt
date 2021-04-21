package domain.exceptions

import domain.model.ErrorCode

class TypeValidationFailedException : DomainException(ErrorCode.InvalidIdentifierFormat)