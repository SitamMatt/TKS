package controllers;

import exceptions.*;
import exceptions.ObjectAlreadyStoredException;
import exceptions.RepositoryException;
import exceptions.ResourceNotAvailableException;
import org.modelmapper.internal.bytebuddy.asm.Advice;
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


    // todo [SPA] remove mode param
    @GET
    @Path("my/{mode}")
    @RolesAllowed("CLIENT")
    @Produces("application/json")
    public Response getMy(@PathParam("mode") String mode) throws UserNotFoundException {
        var login = securityContext.getUserPrincipal().getName();
        var resources = resourcesService.getUserResources(login);
        return Response.ok(resources).build();
    }

    @GET
    @Path("available")
    @RolesAllowed({"CLIENT", "WORKER"})
    @Produces("application/json")
    public Response getAvailable(){
        var resources = resourcesService.getAvailableResources();
        return Response.ok(resources).build();
    }

    @POST
    @Path("{id}/rent")
    @RolesAllowed("CLIENT")
    public Response rent(@PathParam("id") String id) {
        try {
            var guid = UUID.fromString(id);
            var login = securityContext.getUserPrincipal().getName();
            resourcesService.rent(login, guid);
            return Response.ok().build();
        }
        catch (ResourceNotAvailableException e){
            return Response.status(409, "Requested resource is not available. ").build();
        }
        catch (ObjectAlreadyStoredException e){
            return Response.status(409, "Requested object already exists. ").build();
        }
        catch (RepositoryException e){
            return Response.status(409, "Rent cannot be allocated. ").build();
        }
    }

    @POST
    @Path("{id}/return")
    @RolesAllowed("CLIENT")
    public Response returnResource(@PathParam("id") String id) {
        try {
            var guid = UUID.fromString(id);
            var login = securityContext.getUserPrincipal().getName();
            resourcesService.returnResource(login, guid);
            return Response.ok().build();
        }
        catch (ObjectNotFoundException e){
            return Response.status(404, e.getMessage()).build();
        }
        catch (ResourceNotAvailableException e){
            return Response.status(409, "Resource has been already returned. ").build();
        }
        catch (ResourceReturnException e){
            return Response.status(409, "Cannot find matching rental. ").build();
        }
    }
}
