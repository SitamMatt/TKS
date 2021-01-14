package controllers;

import services.ResourcesService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.UUID;
import java.util.logging.Logger;

@Path("resources")
public class ResourcesController {
    private static final Logger LOG = Logger.getLogger(UsersController.class.getName());
    @Inject private ResourcesService resourcesService;
    @Context private SecurityContext securityContext;

    @GET
    @Path("my/{mode}")
//    @RolesAllowed("USER")
    @Produces("application/json")
    public Response getMy(@PathParam("mode") String mode){
        var login = securityContext.getUserPrincipal().getName();
        var resources = resourcesService.getUserResources(login);
        return Response.ok(resources).build();
    }

    @GET
    @Path("available")
//    @RolesAllowed({"USER", "WORKER"})
    @Produces("application/json")
    public Response getAvailable(){
        var resources = resourcesService.getAvailableResources();
        return Response.ok(resources).build();
    }

    @POST
    @Path("{id}/rent")
    @RolesAllowed("USER")
    public Response rent(@PathParam("id") String id) throws Exception {
        var guid = UUID.fromString(id);
        var login = securityContext.getUserPrincipal().getName();
        resourcesService.rent(login, guid);
        return Response.ok().build();
    }

    @POST
    @Path("{id}/return")
    public Response returnResource(@PathParam("id") String id) throws Exception {
        var guid = UUID.fromString(id);
        var login = securityContext.getUserPrincipal().getName();
        resourcesService.returnResource(login, guid);
        return Response.ok().build();
    }
}
