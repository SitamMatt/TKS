//package rest.api.security;
//
//import javax.inject.Inject;
//import javax.security.enterprise.AuthenticationException;
//import javax.security.enterprise.AuthenticationStatus;
//import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
//import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
//import javax.security.enterprise.identitystore.IdentityStoreHandler;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Set;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class JWTAuthenticationMechanism implements HttpAuthenticationMechanism {
//
//    private static final Logger LOG = Logger.getLogger(JWTAuthenticationMechanism.class.getName());
//
//    @Inject
//    private IdentityStoreHandler identityStoreHandler;
//
//    @Override
//    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) throws AuthenticationException {
//        String rawToken = extractToken(context);
//
//        if ("POST".equalsIgnoreCase(request.getMethod()) && request.getRequestURI().contains("/auth/login")) {
//            // Wyłączenie mechanizmu dla /auth/login
//            return context.doNothing();
//        } else if(rawToken != null) {
//            LOG.log(Level.INFO, "Validating jwt token...");
//            return validateToken(rawToken, context);
//        } else if(context.isProtected()) {
//            return context.responseUnauthorized();
//        }
//
//        return context.doNothing();
//    }
//
//    private String extractToken(HttpMessageContext context) {
//        String authorizationHeader = context.getRequest().getHeader("Authorization");
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
//            String token = authorizationHeader.substring("Bearer".length() + 1);
//            LOG.log(Level.INFO, "Extracted token=" + token);
//            return token;
//        }
//        return null;
//    }
//
//    private AuthenticationStatus validateToken(String token, HttpMessageContext context) {
//        if(TokenProvider.isSignatureValid(token) && !TokenProvider.isExpired(token)) {
//            LOG.log(Level.INFO, "JWT signature is valid");
//            JWTCredentialData credentialData = TokenProvider.getCredentialData(token);
//            Set<String> groups = credentialData.getGroups();
//            String subject = credentialData.getSubject();
//
//            if(groups.isEmpty() || subject.isEmpty()) {
//                return context.responseUnauthorized();
//            }
//            return context.notifyContainerAboutLogin(subject, groups);
//        } else {
//            LOG.log(Level.INFO, "JWT signature is not valid");
//            return context.responseUnauthorized();
//        }
//    }
//}
