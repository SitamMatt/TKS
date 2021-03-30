package controllers;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("book/{id}/rent")
public class RentResource {

    // get info
    @GET
    @Path("{id}")
    public Response get(){
        return Response.ok().build();
    }

    // create rent
    @POST
    public Response rent(){
        return Response.ok().build();
    }

    // return book
    @PUT
    public Response returnn(){
        return Response.ok().build();
    }

}
