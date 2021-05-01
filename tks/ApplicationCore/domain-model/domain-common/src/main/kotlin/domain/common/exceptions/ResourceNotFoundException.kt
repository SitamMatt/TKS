package domain.common.exceptions

import domain.common.ErrorCode

class ResourceNotFoundException : DomainException(ErrorCode.ResourceNotFound)