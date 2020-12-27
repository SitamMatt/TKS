package controllers;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import services.ExampleService;

@Path("example")
public class ExampleController {
    @Inject
    private ExampleService service;
    
    
    @GET
    public Response get(){
        return Response.ok().entity(service.getModel()).build();
    }
}
