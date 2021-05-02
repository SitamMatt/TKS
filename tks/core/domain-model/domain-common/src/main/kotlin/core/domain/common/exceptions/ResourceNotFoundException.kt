package core.domain.common.exceptions

import core.domain.common.ErrorCode

class ResourceNotFoundException : DomainException(ErrorCode.ResourceNotFound)