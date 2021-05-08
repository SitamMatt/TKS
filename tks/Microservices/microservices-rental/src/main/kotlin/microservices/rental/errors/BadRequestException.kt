package microservices.rental.errors

import microservices.common.error.Status

class BadRequestException(status: Status) : ServiceException(status) {
}