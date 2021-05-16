package domain.exceptions

import domain.model.ErrorCode

class ResourceNotFoundException : DomainException(ErrorCode.ResourceNotFound)