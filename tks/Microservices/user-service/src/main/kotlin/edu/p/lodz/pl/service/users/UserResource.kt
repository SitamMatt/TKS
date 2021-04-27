package edu.p.lodz.pl.service.users

import common.error.badRequest
import common.error.notFound
import domain.exceptions.TypeValidationFailedException
import domain.exceptions.UserNotFoundException
import edu.p.lodz.pl.service.users.adapters.UserResourceAdapter
import edu.p.lodz.pl.service.users.mappers.UserMapperDto
import org.eclipse.microprofile.openapi.annotations.Operation
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path("user")
open class UserResource {

    @Inject
    private var adapter: UserResourceAdapter? = null

    @Inject
    private var mapper: UserMapperDto? = null

    @GET
    open fun get(): Response? {
        val e = "aaaa";
        val r = e + "555"
        return Response.ok(r).build()
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    @Operation(
        operationId = "Get user info",
        description = "Get the user for the given email",
        summary = "Get user info"
    )
    open fun get(@PathParam("id") email: String?): Response = try {
        val dto = adapter?.queryUser(email)
        Response.ok(dto).build()
    } catch (e: UserNotFoundException) {
        e.error.notFound()
    } catch (e: TypeValidationFailedException) {
        e.error.badRequest()
    }
//
//    @POST
//    @Produces("application/json")
//    @Operation(
//        operationId = "Register new user",
//        description = "Registers new user from given repository.data",
//        summary = "Register new user"
//    )
//    fun post(dto: UserDto?, @Context context: UriInfo): Response = try {
//        adapter?.registerCommand(dto)
//        val resourceLink: URI = context.absolutePathBuilder.path(dto!!.email).build()
//        Response.created(resourceLink).entity(dto).build()
//    } catch (e: DuplicatedEmailException) {
//        e.error.conflict()
//    }
}