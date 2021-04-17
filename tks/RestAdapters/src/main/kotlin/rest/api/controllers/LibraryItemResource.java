package rest.api.controllers;

import rest.api.adapters.LibraryItemResourceAdapter;
import rest.api.dto.LibraryItemDto;
import domain.exceptions.ResourceNotFoundException;
import domain.exceptions.TypeValidationFailedException;
import domain.exceptions.UnknownResourceException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static rest.api.ErrorHelper.badRequest;
import static rest.api.ErrorHelper.notFound;

@Path("library/item")
public class LibraryItemResource {

    @Context private UriInfo context;

    @Inject private LibraryItemResourceAdapter adapter;

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") String id){
        try{
            var dto = adapter.query(id);
            return Response.ok(dto).build();
        } catch (TypeValidationFailedException e) {
            return badRequest(1, "Invalid resource format");
        } catch (ResourceNotFoundException e) {
            return notFound(1, "Resource not found");
        }
    }

    @POST
    public Response post(LibraryItemDto dto){
        try{
            var an = adapter.add(dto);
            var resourceLink = context.getAbsolutePathBuilder().path(an.getValue()).build();
            return Response.created(resourceLink).entity(dto).build();
        }catch (UnknownResourceException e){
            return badRequest(1, "Unknown resource type");
        }
    }
//
//    @PUT
//    public Response put(LibraryItemDto rest.api.dto){
//
//    }
//
//    @DELETE
//    public Response delete(LibraryItemDto rest.api.dto){
//
//    }
}
