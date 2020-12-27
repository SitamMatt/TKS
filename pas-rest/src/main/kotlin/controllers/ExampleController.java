package controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("example")
public class ExampleController {
    @GET
    public Response get(){
        return Response.ok("Hello World").build();
    }
}
