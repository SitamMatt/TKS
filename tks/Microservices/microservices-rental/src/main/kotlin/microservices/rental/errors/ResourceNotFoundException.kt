package microservices.rental.errors

import microservices.common.error.Status

class ResourceNotFoundException : ServiceException(Status.NotFound) {

}