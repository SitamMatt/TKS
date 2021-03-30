//package controllers;
//
//import dto.BookDto;
//
//import javax.ws.rs.*;
//import javax.ws.rs.core.Response;
//
//@Path("book")
//public class BookResource {
//
//    @GET
//    @Path("{id}")
//    public Response get(@PathParam("id") String id){
//        var book = new BookDto();
//        book.setId("elo");
//        book.setAuthor("Johny");
//        book.setTitle("king arthur");
//        return Response.ok(book).build();
//    }
//
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
//}
