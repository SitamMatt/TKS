package controllers;

import dto.LoginDto;
import security.TokenProvider;

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

@Path("auth")
public class AuthController {

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public Response login(@NotNull LoginDto loginData) {
        return getToken(loginData.getEmail(), loginData.getPassword());
    }

    private Response getToken(String login, String password) {
        Credential credential = new UsernamePasswordCredential(login, new Password(password));
        CredentialValidationResult result = identityStoreHandler.validate(credential);
        if(result.getStatus() == CredentialValidationResult.Status.VALID) {
            return Response.accepted()
                    .type("application/json")
                    .entity(TokenProvider.createToken(result))
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
