package controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("users")
public class UserResource {

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") String email){
        return Response.ok(email).build();
    }

}
