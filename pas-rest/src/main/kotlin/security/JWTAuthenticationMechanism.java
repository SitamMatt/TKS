package security;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JWTAuthenticationMechanism implements HttpAuthenticationMechanism {

    private static final Logger LOG = Logger.getLogger(JWTAuthenticationMechanism.class.getName());

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) throws AuthenticationException {
        String rawToken = extractToken(context);

        if ("POST".equalsIgnoreCase(request.getMethod())) {
//            String rawBody = "";
//            try {
//                //rawBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//                ServletInputStream stream = request.getInputStream();
//                rawBody = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
//                stream.close();
//                LOG.log(Level.INFO, "rawBody: " + rawBody);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return context.doNothing();
//            }
//
//            JsonReader reader = Json.createReader(new StringReader(rawBody));
//            JsonObject jsonBody = reader.readObject();
//
//            if (request.getRequestURI().contains("/auth/login")) {
//                LOG.log(Level.INFO, "jsonBody: login='" + jsonBody.getString("login") + "', password='" + jsonBody.getString("password") + "'");
//
//                String login = jsonBody.getString("login");
//                String password = jsonBody.getString("password");
//
//                CredentialValidationResult result = identityStoreHandler.validate(new UsernamePasswordCredential(login, password));
//                if(result.getStatus() == CredentialValidationResult.Status.VALID) {
//                    LOG.log(Level.INFO, "Valid credentials for Principal='"+ result.getCallerPrincipal().getName() + "'");
//
//                    return context.notifyContainerAboutLogin(result.getCallerPrincipal(), result.getCallerGroups());
//                } else {
//                    return context.responseUnauthorized();
//                }
//            }
            // Wyłączenie mechanizmu dla /auth/login
            if (request.getRequestURI().contains("/auth/login")) {
                return context.doNothing();
            } else {
                return context.responseUnauthorized();
            }
        } else if(rawToken != null) {
            return validateToken(rawToken, context);
        } else if(context.isProtected()) {
            return context.responseUnauthorized();
        }

        return context.doNothing();
    }

    private String extractToken(HttpMessageContext context) {
        String authorizationHeader = context.getRequest().getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            String token = authorizationHeader.substring("Bearer".length());
            return token;
        }
        return null;
    }

    private AuthenticationStatus validateToken(String token, HttpMessageContext context) {
        if(TokenProvider.isSignatureValid(token)) {
            JWTCredentialData credentialData = TokenProvider.getCredentialData(token);
            Set<String> groups = credentialData.getGroups();
            String subject = credentialData.getSubject();

            if(groups.isEmpty() || subject.isEmpty()) {
                return context.responseUnauthorized();
            }
            return context.notifyContainerAboutLogin(subject, groups);
        } else {
            return context.responseUnauthorized();
        }

    }
}
