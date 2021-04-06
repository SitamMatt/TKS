package controllers;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("library/item/{id}/rent")
public class RentResource {

    String email = "mszewc@edu.pl";

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
    @DELETE
    public Response returnItem(){
        return Response.ok().build();
    }
}
