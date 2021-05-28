package microservices.common.error

import microservices.common.error.Status

open class ServiceException(val status: Status) : Throwable()