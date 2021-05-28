package microservices.common.error

class ConflictException(status: Status) : ServiceException(status) {
}