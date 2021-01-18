package controllers;


import dto.ResourceBaseDto;
import dto.ResourceGetDto;
import exceptions.ObjectAlreadyStoredException;
import exceptions.ObjectLockedByRentException;
import exceptions.ObjectNotFoundException;
import model.Resource;
import exceptions.RepositoryException;
import security.JWSHelper;
import services.ResourcesService;

import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.EntityTag;
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

    @GET
    //    @RolesAllowed("WORKER")
    @Produces("application/json")
    @Path("{id}")
    public Response get(@PathParam("id") String id){
        try {
            var guid = UUID.fromString(id);
            var resource = resourcesService.find(guid);
            if(resource == null) return Response.status(404).build();

            EntityTag etag = new EntityTag(JWSHelper.sign(resource.getGuid().toString()));

            return Response.ok(resource).header("ETag", etag).build();
        }
        catch (ObjectNotFoundException e){
            return Response.status(404, e.getMessage()).build();
        }

    }

    // todo maybe return createdAt
    // todo handle Exception
    @POST
//    @RolesAllowed("WORKER")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response add(final ResourceBaseDto model) {
        Response res = ValidationController.validate(model);
        if (res!= null) return res;
        try {
            resourcesService.add(model);
            return Response.ok().build();
        } catch (RepositoryException | ObjectAlreadyStoredException e) {
            return Response.status(409, e.getMessage()).build();
        }
    }

    // todo good, but add error handling
    // todo maybe return createdAt
    @PUT
    @Path("{id}")
//    @RolesAllowed("WORKER")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") String id, final ResourceBaseDto model, @NotNull @HeaderParam("If-Match") String ifMatch) throws ObjectNotFoundException, RepositoryException, ObjectLockedByRentException {
        var guid = UUID.fromString(id);
        Response res = ValidationController.validate(model);
        if (res!= null) return res;
        try {
//            var guid = model.getGuid();

            if(JWSHelper.verify(model.getGuid().toString(), ifMatch)) {
                resourcesService.update(guid, model);
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.PRECONDITION_FAILED.getStatusCode(), "Data integrity error.").build();
            }
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
