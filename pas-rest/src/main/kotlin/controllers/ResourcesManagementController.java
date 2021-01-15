package controllers;


import dto.ResourceBaseDto;
import exceptions.ObjectLockedByRentException;
import exceptions.ObjectNotFoundException;
import services.ResourcesService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;
import java.util.logging.Logger;

@Path("resources/management")
public class ResourcesManagementController {

    private static final Logger LOG = Logger.getLogger(UsersController.class.getName());

    @Inject
    private ResourcesService resourcesService;

    // todo good, but add error handling
    @GET
//    @RolesAllowed("WORKER")
    @Produces("application/json")
    public Response get(@QueryParam("type") String type,
                        @QueryParam("page") int page,
                        @QueryParam("maxResults") int maxResults,
                        @QueryParam("search") String search){
        var result = resourcesService.filter(type, page, maxResults, search);
        return Response.ok(result).build();
    }

    // todo good, but add error handling
    @GET
    //    @RolesAllowed("WORKER")
    @Produces("application/json")
    @Path("{id}")
    public Response get(@PathParam("id") String id){
        var guid = UUID.fromString(id);
        var resource = resourcesService.find(guid);
        if(resource == null) Response.status(404).build();
        return Response.ok(resource).build();
    }

    // todo good, but add error handling
    // todo maybe return createdAt
    @POST
//    @RolesAllowed("WORKER")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response add(final ResourceBaseDto model) throws Exception {
        resourcesService.add(model);
        return Response.ok().build();
    }

    // todo good, but add error handling
    // todo maybe return createdAt
    @PUT
    @Path("{id}")
//    @RolesAllowed("WORKER")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") String id, final ResourceBaseDto model) throws Exception {
        var guid = UUID.fromString(id);
        resourcesService.update(guid, model);
        return Response.ok().build();
    }

    // todo good, but add error handling
    @DELETE
    @Path("{id}")
//    @RolesAllowed("WORKER")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) throws ObjectLockedByRentException, ObjectNotFoundException {
        var guid = UUID.fromString(id);
        resourcesService.delete(guid);
        return Response.ok().build();
    }
}