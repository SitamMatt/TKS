package controllers;

import adapters.RentResourceAdapter;
import domain.exceptions.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static application.helpers.ErrorHelper.*;

@Path("library/item/{id}/rent")
public class RentResource {

    String email = "mszewc@edu.pl";

    @Inject
    private RentResourceAdapter adapter;

    // get info
    @GET
    @Path("{id}")
    public Response get(){
        return Response.ok().build();
    }

    // create rent
    @POST
    public Response rent(@PathParam("id") String id){
        try{
            adapter.rent(email, id);
            return Response.ok().build();
        } catch (ResourceAlreadyRentException e) {
            return conflict(1, "Resource already rent");
        } catch (ResourceNotFoundException e) {
            return notFound(1, "Resource not found");
        } catch (UserNotFoundException e) {
            return notFound(1, "User not found");
        } catch (UserNotActiveException e) {
            return badRequest(1, "User not active");
        }
    }

    // return book
    @DELETE
    public Response returnItem(@PathParam("id") String id){
        try{
            adapter.returnItem(email, id);
            return Response.ok().build();
        } catch (ResourceNotFoundException e) {
            return notFound(1, "Resource not found");
        } catch (UserNotFoundException e) {
            return notFound(1, "User not found");
        } catch (ResourceNotRentException e) {
            return badRequest(1, "Resource not rent");
        } catch (InvalidUserException e) {
            return badRequest(1, "Invalid user");
        }
    }
}
