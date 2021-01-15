package controllers;


import dto.UserBaseDto;
import dto.UserCreateDto;
import exceptions.ObjectAlreadyStoredException;
import exceptions.ObjectNotFoundException;
import exceptions.RepositoryException;
import services.UsersService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.UUID;
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
            return Response.ok(user).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(404, e.getMessage()).build();
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
            return Response.ok(user).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(404, e.getMessage()).build();
        }
    }

    // todo maybe return createdAt
    @POST
    @RolesAllowed("ADMIN")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response add(final UserCreateDto model) throws ObjectAlreadyStoredException, RepositoryException, ObjectNotFoundException {
        Response res = ValidationController.validate(model);
        if (res != null) return res;
        try {
            usersService.add(model);
            return Response.ok().build();
        } catch (ObjectAlreadyStoredException e) {
            return Response.status(405, "Requested object already exists. ").build();
        } catch (RepositoryException e) {
            return Response.status(409, e.getMessage()).build();
        }
    }

    // todo good, but add error handling
    // todo maybe return createdAt
    @PUT
    @Path("{id}")
    @RolesAllowed("ADMIN")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") String id, final UserCreateDto model) throws ObjectAlreadyStoredException, RepositoryException, ObjectNotFoundException {
        Response res = ValidationController.validate(model);
        if (res != null) return res;
        try {
            var guid = UUID.fromString(id);
            usersService.update(guid, model);
            return Response.ok().build();
        } catch (RepositoryException e) {
            return Response.status(409, e.getMessage()).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(404, e.getMessage()).build();
        }

//    @GET
//    @Path("userdata")
//    @RolesAllowed("ADMIN")
//    public Response getUserData(@NotNull @QueryParam("login") String login) {
//        UserDto user = usersService.find(login);
//        if(user == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        return Response.ok().entity(user).build();
//    }

//    private Response saveData(@NotNull UserDto userData) {
//        try {
//            usersService.save(userData);
//        } catch (ObjectNotFoundException e) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        } catch (ObjectAlreadyStoredException | RepositoryException e) {
//            return Response.status(Response.Status.CONFLICT).build();
//        }
//        return Response.ok().build();
//    }
    }
}
