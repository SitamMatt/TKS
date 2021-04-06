package helpers

import javax.ws.rs.core.Response

fun domain.model.Error.notFound(): Response? {
    return Response
        .status(Response.Status.NOT_FOUND)
        .entity(dto.Error(this.message, this.code))
        .build()
}

fun domain.model.Error.conflict(): Response? {
    return Response
        .status(Response.Status.CONFLICT)
        .entity(dto.Error(this.message, this.code))
        .build()
}

fun domain.model.Error.badRequest(): Response? {
    return Response
        .status(Response.Status.BAD_REQUEST)
        .entity(dto.Error(this.message, this.code))
        .build()
}