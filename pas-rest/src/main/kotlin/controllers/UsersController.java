package controllers;


import dto.UserCreateDto;
import exceptions.ObjectAlreadyStoredException;
import exceptions.ObjectNotFoundException;
import exceptions.RepositoryException;
import security.JWSHelper;
import services.UsersService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("users")
public class UsersController {

    private static final Logger LOG = Logger.getLogger(UsersController.class.getName());

    @Context
    private SecurityContext securityContext;

    @Inject
    private UsersService usersService;

    @GET
    @RolesAllowed("ADMIN")
    @Produces("application/json")
    public Response get(@QueryParam("type") String type,
                        @QueryParam("page") int page,
                        @QueryParam("maxResults") int maxResults,
                        @QueryParam("search") String search) {
        var result = usersService.filter(type, page, maxResults, search);
        if(result.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(result).build();
    }

    // todo good, but add error handling
    @GET
    @Path("{id}")
    @RolesAllowed("ADMIN")
    @Produces("application/json")
    public Response get(@PathParam("id") String id) {
        try {
            var guid = UUID.fromString(id);
            var user = usersService.find(guid);
            if(user == null) return Response.status(404).build();

            EntityTag etag = new EntityTag(JWSHelper.sign(user.getLogin()));
            return Response.ok(user).header("ETag", etag).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(404, e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), "Wrong guid format.").build();
        }

    }

    @GET
    @Path("me")
    @RolesAllowed({"ADMIN", "WORKER", "CLIENT"})
    @Produces("application/json")
    public Response getMe() {
        try {
            var login = securityContext.getUserPrincipal().getName();
            var user = usersService.find(login);
            if(user == null) return Response.status(404).build();

            EntityTag etag = new EntityTag(JWSHelper.sign(user.getLogin()));

            return Response.ok(user).header("ETag", etag).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(404, e.getMessage()).build();
        }
    }

    // todo maybe return createdAt
    @POST
    @RolesAllowed("ADMIN")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response add(@NotNull final UserCreateDto model) {
        Response res = ValidationController.validate(model);
        if (res != null) return res;

        try {
            usersService.find(model.getLogin());
            return Response.status(Response.Status.CONFLICT).build();
        } catch (ObjectNotFoundException ignored) { }

        try {
            usersService.add(model);
            return Response.status(Response.Status.CREATED).build();
        } catch (ObjectAlreadyStoredException e) {
            return Response.status(409, "Requested object already exists. ").build();
        } catch (RepositoryException e) {
            return Response.status(400, "User could not be added. ").build();
        }

    }

    // todo good, but add error handling
    // todo maybe return createdAt
    @PUT
    @Path("{id}")
    @RolesAllowed("ADMIN")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") String id, final UserCreateDto model, @NotNull @HeaderParam("If-Match") String ifMatch) {
        var guid = UUID.fromString(id);
        Response res = ValidationController.validate(model);
        if (res != null) return res;
        try {
//            var guid = model.getGuid();

            var user = usersService.find(model.getLogin());

            if(JWSHelper.verify(user.getLogin(), ifMatch)) {
                usersService.update(guid, model);
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.PRECONDITION_FAILED.getStatusCode(), "Data integrity error.").build();
            }
        } catch (RepositoryException e) {
            return Response.status(409, "Requested user could not be updated. ").build();
        } catch (ObjectNotFoundException e) {
            return Response.status(404, e.getMessage()).build();
        }
    }
}
