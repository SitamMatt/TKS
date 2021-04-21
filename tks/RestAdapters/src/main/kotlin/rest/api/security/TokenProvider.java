//package rest.api.security;
//
//import com.nimbusds.jose.*;
//import com.nimbusds.jose.crypto.MACSigner;
//import com.nimbusds.jose.crypto.MACVerifier;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.SignedJWT;
//import com.nimbusds.jwt.proc.BadJWTException;
//import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
//import rest.api.dto.TokenDto;
//
//import javax.security.enterprise.identitystore.CredentialValidationResult;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//
//public class TokenProvider {
//    private static final String SECRET = "a0a2abd8-6162-41c3-83d6-1cf559b46afc";
//    //    private static final long JWT_TIMEOUT = 15 * 1000 * 60;
//    private static final long JWT_TIMEOUT = 60 * 1000;
//    private static final Logger LOG = Logger.getLogger(TokenProvider.class.getName());
//
//    public static final String AUTH_CLAIM = "auth";
//
//    public static TokenDto createToken(CredentialValidationResult credential) {
//        try {
//            JWSSigner signer = new MACSigner(SECRET);
//            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
//                    .subject(credential.getCallerPrincipal().getName())
//                    .claim(AUTH_CLAIM, String.join(",", credential.getCallerGroups()))
//                    .issuer("PAS Rest Api")
//                    .expirationTime(new Date(new Date().getTime() + JWT_TIMEOUT))
//                    .build();
//
//            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
//            signedJWT.sign(signer);
//
//            String token = signedJWT.serialize();
//            var formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
//
//
//            LOG.log(Level.INFO, "JWT TOKEN GENERATED="+token);
//            return new TokenDto(token, formatter.format(claimsSet.getExpirationTime()));
//        } catch (JOSEException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static JWTCredentialData getCredentialData(String token) {
//        Set<String> groups = new HashSet<>();
//        String subject = "";
//        try {
//            SignedJWT signedJWT = SignedJWT.parse(token);
//            JWTClaimsSet set = signedJWT.getJWTClaimsSet();
//            Map<String, Object> claims = set.getClaims();
//            groups = new HashSet<>(Arrays.asList(claims.get(AUTH_CLAIM).toString().split(",")));
//            subject = set.getSubject();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return new JWTCredentialData(groups, subject);
//    }
//
//    public static boolean isSignatureValid(String token) {
//        SignedJWT signedJWT;
//        try {
//            signedJWT = SignedJWT.parse(token);
//            JWSVerifier verifier = new MACVerifier(SECRET);
//            return signedJWT.verify(verifier);
//        } catch (ParseException | JOSEException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public static boolean isExpired(String token) {
//        SignedJWT signedJWT;
//        try {
//            signedJWT = SignedJWT.parse(token);
//            DefaultJWTClaimsVerifier jwtClaimsSetVerifier = new DefaultJWTClaimsVerifier();
//            int timestampSkew = 120;
//            jwtClaimsSetVerifier.setMaxClockSkew(timestampSkew);
//            jwtClaimsSetVerifier.verify(signedJWT.getJWTClaimsSet());
//            return false;
//        } catch (ParseException | BadJWTException e) {
//            e.printStackTrace();
//            return true;
//        }
//    }
//}
//
