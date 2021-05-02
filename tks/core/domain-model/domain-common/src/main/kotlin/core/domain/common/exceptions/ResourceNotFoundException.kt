package core.domain.common.exceptions

import core.domain.common.ErrorCode

class ResourceNotFoundException : core.domain.common.exceptions.DomainException(core.domain.common.ErrorCode.ResourceNotFound)