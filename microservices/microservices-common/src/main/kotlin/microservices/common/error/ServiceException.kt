package microservices.common.error

open class ServiceException(val status: Status) : Throwable()