package rest.api.controllers

import application.helpers.badRequest
import application.helpers.notFound
import domain.exceptions.ResourceNotFoundException
import domain.exceptions.TypeValidationFailedException
import domain.exceptions.UnknownResourceException
import rest.api.ErrorHelper
import rest.api.adapters.LibraryItemResourceAdapter
import rest.api.dto.LibraryItemDto
import java.net.URI
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Path("library/item")
class LibraryItemResource(
    private val adapter: LibraryItemResourceAdapter
) {

    @GET
    @Path("{id}")
    operator fun get(@PathParam("id") id: String?): Response = try {
        val dto = adapter.query(id)
        Response.ok(dto).build()
    } catch (e: TypeValidationFailedException) {
        e.error.badRequest()
    } catch (e: ResourceNotFoundException) {
        e.error.notFound()
    }

    @POST
    fun post(dto: LibraryItemDto?, @Context context: UriInfo): Response = try {
        val id = adapter.add(dto)
        val resourceLink: URI = context.absolutePathBuilder.path(id).build()
        Response.created(resourceLink).entity(dto).build()
    } catch (e: UnknownResourceException) {
        e.error.badRequest()
    }
}