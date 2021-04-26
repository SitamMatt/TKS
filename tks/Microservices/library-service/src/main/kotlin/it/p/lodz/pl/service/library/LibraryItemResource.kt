package it.p.lodz.pl.service.library

import common.error.badRequest
import common.error.notFound
import domain.exceptions.ResourceNotFoundException
import domain.exceptions.TypeValidationFailedException
import domain.exceptions.UnknownResourceException
import it.p.lodz.pl.service.library.adapters.LibraryItemResourceAdapter
import it.p.lodz.pl.service.library.dto.LibraryItemDto
import java.net.URI
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Path("library/item")
open class LibraryItemResource @Inject constructor(
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