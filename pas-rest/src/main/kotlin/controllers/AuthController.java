package controllers;

import model.LoginData;
import security.TokenProvider;
import services.UsersService;
import services.dto.UserDto;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
@Path("auth")
public class AuthController {

    private static final Logger LOG = Logger.getLogger(AuthController.class.getName());

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Context
    private SecurityContext securityContext;

    @Inject
    private UsersService usersService;


    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public Response login(@NotNull LoginData loginData) {
        LOG.log(Level.INFO, "auth/login: login='" + loginData.getLogin() + "', password='" + loginData.getPassword() + "'");
        return getToken(loginData.getLogin(), loginData.getPassword());
    }


    @GET
    @Path("refresh")
    @RolesAllowed({"ADMIN", "WORKER", "CLIENT"})
    public Response refresh() {
        UserDto user = usersService.find(securityContext.getUserPrincipal().getName());
        if(user != null && user.isActive()) {
            return getToken(user.getLogin(), user.getPassword());
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private Response getToken(String login, String password) {
        Credential credential = new UsernamePasswordCredential(login, new Password(password));
        CredentialValidationResult result = identityStoreHandler.validate(credential);
        if(result.getStatus() == CredentialValidationResult.Status.VALID) {
            return Response.accepted()
                    .type("application/jwt")
                    .entity(TokenProvider.createToken(result))
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
