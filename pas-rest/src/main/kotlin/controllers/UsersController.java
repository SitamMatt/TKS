package controllers;

import com.nimbusds.jose.shaded.json.JSONObject;
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
                        @QueryParam("search") String search){
        var result = usersService.filter(type, page, maxResults, search);
        return Response.ok(result).build();
    }

    // todo good, but add error handling
    @GET
    @Path("{id}")
    @RolesAllowed("ADMIN")
    @Produces("application/json")
    public Response get(@PathParam("id") String id){
        var guid = UUID.fromString(id);
        var user = usersService.find(guid);
        if(user == null) return Response.status(404).build();

        EntityTag etag = new EntityTag(JWSHelper.sign(user.getLogin()));

        return Response.ok(user).header("ETag", etag).build();
    }

    @GET
    @Path("me")
    @RolesAllowed({"ADMIN", "WORKER", "CLIENT"})
    @Produces("application/json")
    public Response getMe() {
        var login = securityContext.getUserPrincipal().getName();
        var user = usersService.find(login);
        if(user == null) return Response.status(404).build();

        EntityTag etag = new EntityTag(JWSHelper.sign(user.getLogin()));

        return Response.ok(user).header("ETag", etag).build();
    }

    // todo maybe return createdAt
    @POST
    @RolesAllowed("ADMIN")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response add(final UserCreateDto model) throws ObjectAlreadyStoredException, RepositoryException {
        usersService.add(model);
        return Response.ok().build();
    }

    // todo good, but add error handling
    // todo maybe return createdAt
    @PUT
//    @Path("{id}")
    @RolesAllowed("ADMIN")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(final UserCreateDto model, @NotNull @HeaderParam("If-Match") String ifMatch) throws RepositoryException, ObjectNotFoundException {
        var guid = model.getGuid();

        var user = usersService.find(model.getLogin());

        if(JWSHelper.verify(user.getLogin(), ifMatch)) {
            usersService.update(guid, model);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.PRECONDITION_FAILED.getStatusCode(), "Data integrity error.").build();
        }
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
