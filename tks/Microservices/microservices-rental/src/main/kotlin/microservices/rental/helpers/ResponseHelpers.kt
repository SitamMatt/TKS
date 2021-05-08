package microservices.rental.helpers

import microservices.common.error.ErrorDto
import microservices.common.error.Status
import javax.ws.rs.core.Response

fun notFound(status: Status): Response {
    return Response.status(Response.Status.NOT_FOUND).entity(ErrorDto(status)).build()
}

fun conflict(status: Status): Response {
    return Response.status(Response.Status.CONFLICT).entity(ErrorDto(status)).build()
}