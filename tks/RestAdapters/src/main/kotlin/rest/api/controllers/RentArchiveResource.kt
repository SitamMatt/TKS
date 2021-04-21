package rest.api.controllers

import application.helpers.notFound
import domain.exceptions.RentNotFoundException
import rest.api.adapters.RentResourceAdapter
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response

@Path("rent/{id}")
open class RentArchiveResource @Inject constructor(
    private val adapter: RentResourceAdapter,
) {

    @GET
    @Path("{id}")
    fun get(@PathParam("id") id: String?): Response = try {
        val result = adapter.queryCommand(id!!)
        Response.ok(result).build()
    } catch (e: RentNotFoundException) {
        e.error.notFound()
    }
}