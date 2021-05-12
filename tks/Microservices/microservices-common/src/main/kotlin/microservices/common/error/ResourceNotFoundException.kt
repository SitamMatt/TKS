package microservices.common.error

class ResourceNotFoundException : ServiceException(Status.NotFound) {

}