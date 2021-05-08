package microservices.rental.controllers

import microservices.rental.adapters.RentalServiceAdapter
import microservices.rental.errors.ConflictException
import microservices.rental.errors.ResourceNotFoundException
import microservices.rental.helpers.conflict
import microservices.rental.helpers.notFound
import microservices.rental.links.LinksResolver
import microservices.rental.mappers.ExampleClass
import repositories.rental.ELO
import java.util.*
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo


@Path("rent")
class RentResource @Inject constructor(
    private val adapter: RentalServiceAdapter,
) {

    @Context
    private lateinit var uriInfo: UriInfo

    @Inject
    private lateinit var elo: ELO

    @Inject
    private lateinit var example: ExampleClass

    @PersistenceContext(name = "rentalPU")
    private lateinit var em: EntityManager

    @GET
    @Path("{id}")
    @Produces("application/json")
    fun get(@PathParam("id") id: UUID): Response = adapter.queryRent(id)
        .map {
            LinksResolver.resolve(it, it::links, uriInfo)
            return Response.ok(it).build()
        }.getOrElse {
            return when (it) {
                is ResourceNotFoundException -> notFound(it.status)
                else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
            }
        }

    @POST
    @Path("{productId}")
    fun post(@PathParam("productId") productId: String): Response = adapter.createRentByUser(productId)
        .map {
            LinksResolver.resolve(it, it::links, uriInfo)
            val selfLink = it.links.first { x -> x.rel == "self" }
            return Response.created(selfLink.uri).entity(it).build()
        }.getOrElse {
            return when (it) {
                is ResourceNotFoundException -> notFound(it.status)
                is ConflictException -> conflict(it.status)
                else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
            }
        }

    @PUT
    @Path("{id}/return")
    fun put(@PathParam("id") id: UUID): Response = adapter.finalizeRentByUser(id)
        .map {
            LinksResolver.resolve(it, it::links, uriInfo)
            return Response.ok().entity(it).build()
        }.getOrElse {
            return when (it) {
                is ResourceNotFoundException -> notFound(it.status)
                else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
            }
        }

}