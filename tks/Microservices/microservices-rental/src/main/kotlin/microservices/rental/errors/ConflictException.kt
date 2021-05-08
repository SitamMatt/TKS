package microservices.rental.errors

import microservices.common.error.Status

class ConflictException(status: Status) : ServiceException(status) {
}