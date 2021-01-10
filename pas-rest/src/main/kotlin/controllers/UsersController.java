package controllers;


import jdk.net.SocketFlow;
import model.LoginData;
import model.User;
import model.UserRole;
import model.exceptions.ObjectAlreadyStoredException;
import model.exceptions.ObjectNotFoundException;
import model.exceptions.RepositoryException;
import services.UsersService;
import services.dto.UserDto;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
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
    public Response get() {
        return Response.ok().entity(usersService.getAllUsers()).build();
    }
    
    @GET
    @Path("mydata")
    @RolesAllowed({"ADMIN", "WORKER", "CLIENT"})
    public Response getMyData() {
        return Response.ok().entity(usersService.find(securityContext.getUserPrincipal().getName())).build();
    }

    @POST
    @Path("adduser")
    @RolesAllowed("ADMIN")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addUser(@NotNull LoginData loginData,
                            @DefaultValue("CLIENT") @QueryParam("role") String role,
                            @DefaultValue("true") @QueryParam("isActive") boolean isActive,
                            @QueryParam("firstName") String firstName,
                            @QueryParam("lastName") String lastName) {
        UserDto userDto = new UserDto(UUID.randomUUID(), isActive, UserRole.fromString(role), firstName,
                lastName, loginData.getLogin(), loginData.getPassword());
        return saveData(userDto);
    }

    @PUT
    @Path("updateuser")
    @RolesAllowed({"ADMIN", "CLIENT"})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@NotNull UserDto userData) {
        if(securityContext.isUserInRole("CLIENT") && !securityContext.getUserPrincipal().getName().equals(userData.getLogin())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(usersService.getAllUsers().stream().anyMatch(u -> u.getId().equals(userData.getId()))) {
            return saveData(userData);
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("userdata")
    @RolesAllowed("ADMIN")
    public Response getUserData(@NotNull @QueryParam("login") String login) {
        UserDto user = usersService.find(login);
        if(user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(user).build();
    }

    private Response saveData(@NotNull UserDto userData) {
        try {
            usersService.save(userData);
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ObjectAlreadyStoredException | RepositoryException e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.ok().build();
    }

}
