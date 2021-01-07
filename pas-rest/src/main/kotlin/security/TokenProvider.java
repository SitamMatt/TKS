package security;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import javax.security.enterprise.identitystore.CredentialValidationResult;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


public class TokenProvider {
    private static final String SECRET = "NRIghkFeRJ0hoqNUawtuNRIghkFeRJ0hoqNUawtuNRIghkFeRJ0hoqNUawtu";
    private static final long JWT_TIMEOUT = 15 * 1000 * 60;

    public static final String AUTH_CLAIM = "auth";

    public static String createToken(CredentialValidationResult credential) {
        try {
            JWSSigner signer = new MACSigner(SECRET);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(credential.getCallerPrincipal().getName())
                    .claim(AUTH_CLAIM, String.join(",", credential.getCallerGroups()))
                    .issuer("PAS Rest Api")
                    .expirationTime(new Date(new Date().getTime() + JWT_TIMEOUT))
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static JWTCredentialData getCredentialData(String token) {
        Set<String> groups = new HashSet<>();
        String subject = "";
        try {
            JWTClaimsSet set = JWTClaimsSet.parse(token);
            Map<String, Object> claims = set.getClaims();
            groups = new HashSet<>(Arrays.asList(claims.get(AUTH_CLAIM).toString().split(",")));
            subject = set.getSubject();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new JWTCredentialData(groups, subject);
    }

    public static boolean isSignatureValid(String token) {
        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET);
            return signedJWT.verify(verifier);
        } catch (ParseException | JOSEException e) {
            return false;
        }
    }
}
