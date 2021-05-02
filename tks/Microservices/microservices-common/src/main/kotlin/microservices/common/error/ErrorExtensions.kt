package microservices.common.error

import domain.model.ErrorCode
import javax.ws.rs.core.Response

fun ErrorCode.notFound(): Response {
    return Response
        .status(Response.Status.NOT_FOUND)
        .entity(ErrorDto(this.message, this.code))
        .build()
}

fun ErrorCode.conflict(): Response {
    return Response
        .status(Response.Status.CONFLICT)
        .entity(ErrorDto(this.message, this.code))
        .build()
}

fun ErrorCode.badRequest(): Response {
    return Response
        .status(Response.Status.BAD_REQUEST)
        .entity(ErrorDto(this.message, this.code))
        .build()
}

