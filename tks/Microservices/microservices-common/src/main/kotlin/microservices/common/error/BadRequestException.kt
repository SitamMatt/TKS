package microservices.common.error

class BadRequestException(status: Status) : ServiceException(status) {
}