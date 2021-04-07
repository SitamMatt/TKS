package rest.api.application.helpers

import rest.api.dto.ErrorDto
import javax.ws.rs.core.Response

    fun domain.model.ErrorCode.notFound(): Response? {
        return Response
            .status(Response.Status.NOT_FOUND)
            .entity(ErrorDto(this.message, this.code))
            .build()
    }

    fun domain.model.ErrorCode.conflict(): Response? {
        return Response
            .status(Response.Status.CONFLICT)
            .entity(ErrorDto(this.message, this.code))
            .build()
    }

    fun domain.model.ErrorCode.badRequest(): Response? {
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(ErrorDto(this.message, this.code))
            .build()
    }

