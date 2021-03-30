package controllers;


import dto.UserDto;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("user")
public class UserResource {

    @GET
    @Path("{id}")
    @Produces("application/json")
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
        return Response.ok().build();
    }

    // update user
    // by admin
    // password is ignored
    @PUT
    public Response put(UserDto dto){
        return Response.ok().build();
    }





}
