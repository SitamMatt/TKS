package controllers;


import dto.UserDto;
import org.eclipse.microprofile.openapi.annotations.Operation;
import services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("user")
public class UserResource {

    @Context
    private UriInfo context;

    @Inject
    private UserService userService;

    @GET
    @Path("{id}")
    @Produces("application/json")
    @Operation(operationId = "Get User Info", description = "Get the user for the given email")
    public Response get(@PathParam("id") String email){
        var resource = new UserDto();
        resource.setActive(true);
        resource.setEmail("mszewc@edu.pl");
        resource.setPassword("####");
        resource.setRole("Loczek");
        return Response.ok(resource).build();
    }

    // creating new user
    // by admin
    @POST
    public Response post(UserDto dto){


        var resourceLink = context.getAbsolutePathBuilder().path(dto.getEmail()).build();
        return Response.created(resourceLink).entity(dto).build();
    }

    // update user
    // by admin
    // password is ignored
    @PUT
    public Response put(UserDto dto){
        return Response.ok().build();
    }





}
