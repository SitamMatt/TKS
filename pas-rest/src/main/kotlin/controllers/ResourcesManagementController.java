package controllers;


import dto.ResourceBaseDto;
import dto.ResourceGetDto;
import exceptions.ObjectLockedByRentException;
import exceptions.ObjectNotFoundException;
import model.Resource;
import services.ResourcesService;

import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
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
        try {
            var guid = UUID.fromString(id);
            var resource = resourcesService.find(guid);
            return Response.ok(resource).build();
        }
        catch (ObjectNotFoundException e){
            return Response.status(404, e.getMessage()).build();
        }
    }

    // todo good, but add error handling
    // todo maybe return createdAt
    // todo handle Exception
    @POST
//    @RolesAllowed("WORKER")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response add(final ResourceBaseDto model) throws Exception {
        Response res = ValidationController.validate(model);
        if (res!= null) return res;
        try {
            resourcesService.add(model);
            return Response.ok().build();
        }
        catch (Exception e){
            return Response.status(409, e.getMessage()).build();
        }
    }

    // todo good, but add error handling
    // todo maybe return createdAt
    @PUT
    @Path("{id}")
//    @RolesAllowed("WORKER")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") String id, final ResourceBaseDto model) throws Exception {
        var guid = UUID.fromString(id);
        Response res = ValidationController.validate(model);
        if (res!= null) return res;
        try {
            resourcesService.update(guid, model);
            return Response.ok().build();
        }
        catch (ObjectNotFoundException e){
            return Response.status(404, e.getMessage()).build();
        }
    }

    // todo good, but add error handling
    @DELETE
    @Path("{id}")
//    @RolesAllowed("WORKER")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) {
        try {
            var guid = UUID.fromString(id);
            resourcesService.delete(guid);
            return Response.ok().build();
        }
        catch (ObjectLockedByRentException e){
            return Response.status(405, "Requested resource is locked by ongoing rent. ").build();
        }
        catch (ObjectNotFoundException e){
            return Response.status(404, e.getMessage()).build();
        }
    }
}
