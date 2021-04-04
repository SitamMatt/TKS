package controllers;

import dto.BookDto;
import ports.primary.IResourceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("library/item")
public class LibraryItemResource {

    @Inject private IResourceService resourceService;

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") String id){
        var book = new BookDto();
        book.setId("elo");
        book.setAuthor("Johny");
        book.setTitle("king arthur");
        return Response.ok(book).build();
    }

//    @POST
//    public Response post(BookDto dto){
//
//    }
//
//    @PUT
//    public Response put(BookDto dto){
//
//    }
//
//    @DELETE
//    public Response delete(BookDto dto){
//
//    }
}
