package rest.api.controllers;


import rest.api.adapters.UserResourceAdapter;
import domain.exceptions.DuplicatedEmailException;
import domain.exceptions.TypeValidationFailedException;
import domain.exceptions.UserNotFoundException;
import rest.api.dto.UserDto;
import rest.api.application.helpers.ErrorExtensionsKt;
import rest.api.mappers.UserMapperDto;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static rest.api.application.helpers.ErrorHelper.badRequest;

@Path("user")
public class UserResource {

    @Context
    private UriInfo context;

    @Inject
    private UserMapperDto mapper;

    @Inject
    private UserResourceAdapter adapter;

    @GET
    @Path("{id}")
    @Produces("application/json")
    @Operation(
            operationId = "Get user info",
            description = "Get the user for the given email",
            summary = "Get user info"
    )
    public Response get(@PathParam("id") String email) {
        try {
            var dto = adapter.queryUser(email);
            return Response.ok(dto).build();
        } catch (UserNotFoundException e) {
            return ErrorExtensionsKt.notFound(e.getError());
        } catch (TypeValidationFailedException e) {
            return badRequest(1, "Invalid parameter");
        }
    }

    @POST
    @Produces("application/json")
    @Operation(
            operationId = "Register new user",
            description = "Registers new user from given data",
            summary = "Register new user"
    )
    public Response post(UserDto dto) {
        try {
            var email = adapter.registerCommand(dto);
            var resourceLink = context.getAbsolutePathBuilder().path(email).build();
            return Response.created(resourceLink).entity(dto).build();
        } catch (DuplicatedEmailException e) {
            return ErrorExtensionsKt.conflict(e.getError());
        }
    }

//    // update user
//    // by admin
//    // password is ignored
//    @PUT
//    public Response put(UserDto rest.api.dto) {
//        return Response.ok().build();
//    }
}
