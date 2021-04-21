package rest.api.controllers

import application.helpers.badRequest
import application.helpers.conflict
import application.helpers.notFound
import domain.exceptions.*
import rest.api.ErrorHelper
import rest.api.adapters.RentResourceAdapter
import java.net.URI
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Path("library/item/{resourceId}/rent/by/{email}")
class RentResource @Inject constructor(
    private val adapter: RentResourceAdapter,
){
    // get info
//    @GET
//    @Path("{id}")
//    fun get(@PathParam("id") id: String?): Response {
////        adapter.
//        return Response.ok().build()
//    }

    @POST
    fun rent(@PathParam("resourceId") resourceId: String?, @PathParam("email") email: String?, @Context context: UriInfo): Response = try {
        val id = adapter.rent(email!!, resourceId!!)
        val resourceLink: URI = context.absolutePathBuilder.path(id.toString()).build()
        Response.created(resourceLink).build()
    } catch (e: ResourceAlreadyRentException) {
        e.error.conflict()
    } catch (e: ResourceNotFoundException) {
        e.error.notFound()
    } catch (e: UserNotFoundException) {
        e.error.notFound()
    } catch (e: UserNotActiveException) {
        e.error.badRequest()
    } catch (e: TypeValidationFailedException){
        e.error.badRequest()
    }

    @DELETE
    fun returnItem(@PathParam("resourceId") resourceId: String?, @PathParam("email") email: String?): Response = try {
        adapter.returnItem(email!!, resourceId!!)
        Response.ok().build()
    } catch (e: ResourceNotFoundException) {
        e.error.notFound()
    } catch (e: UserNotFoundException) {
        e.error.notFound()
    } catch (e: ResourceNotRentException) {
        e.error.badRequest()
    } catch (e: InvalidUserException) {
        e.error.badRequest()
    } catch (e: TypeValidationFailedException){
        e.error.badRequest()
    }
}