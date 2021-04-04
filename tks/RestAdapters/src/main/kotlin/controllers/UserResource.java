package controllers;


import adapters.UserResourceAdapter;
import dto.Error;
import dto.UserDto;
import exceptions.DuplicatedEmailException;
import exceptions.UserNotFoundException;
import mappers.UserMapperDto;
import org.eclipse.microprofile.openapi.annotations.Operation;
import services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Objects;

import static helpers.ErrorHelper.conflict;
import static helpers.ErrorHelper.notFound;

@Path("user")
public class UserResource {

    @Context
    private UriInfo context;

    @Inject
    private UserService userService;

    @Inject
    private UserMapperDto mapper;

    @Inject
    private UserResourceAdapter adapter;

    @GET
    @Path("{id}")
    @Produces("application/json")
    @Operation(operationId = "Get user info", description = "Get the user for the given email", summary = "Get user info")
    public Response get(@PathParam("id") String email) {
        try {
            var user = userService.getDetails(email);
            var dto = mapper.toDto(user);
            return Response.ok(dto).build();
        } catch (UserNotFoundException e) {
            return notFound(1, "User not found");
        }
    }

    @POST
    @Produces("application/json")
    @Operation(operationId = "Register new user", description = "Registers new user from given data", summary = "Register new user")
    public Response post(UserDto dto) {
        try {
            var email = adapter.registerCommand(dto);
            var resourceLink = context.getAbsolutePathBuilder().path(email).build();
            return Response.created(resourceLink).entity(dto).build();
        } catch (DuplicatedEmailException e) {
            return conflict(1, "Email already taken");
        }
    }

//    // update user
//    // by admin
//    // password is ignored
//    @PUT
//    public Response put(UserDto dto) {
//        return Response.ok().build();
//    }
}
