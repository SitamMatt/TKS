package controllers;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import repositories.interfaces.IUsersRepository;
import services.ExampleService;
import services.UsersService;

@Path("example")
public class ExampleController {
    @Inject
    private ExampleService service;
    @Inject
    private UsersService usersService;
    
    @GET
    public Response get(){
        return Response.ok().entity(service.getModel()).build();
    }

    @GET
    @Path("ping")
    public Response ping(){
        return Response.ok().entity(usersService.find("testo")).build();
    }
}
