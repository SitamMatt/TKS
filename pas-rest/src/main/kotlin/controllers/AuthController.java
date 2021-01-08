package controllers;

import model.LoginData;
import security.TokenProvider;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
@Path("auth")
public class AuthController {

    private static final Logger LOG = Logger.getLogger(AuthController.class.getName());

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public Response login(@NotNull LoginData loginData) {
        LOG.log(Level.INFO, "auth/login: login='" + loginData.getLogin() + "', password='" + loginData.getPassword() + "'");
        Credential credential = new UsernamePasswordCredential(loginData.getLogin(), new Password(loginData.getPassword()));
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
