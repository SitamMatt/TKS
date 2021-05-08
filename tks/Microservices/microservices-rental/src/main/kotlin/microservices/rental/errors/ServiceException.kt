package microservices.rental.errors

import microservices.common.error.Status

open class ServiceException(val status: Status) : Throwable()