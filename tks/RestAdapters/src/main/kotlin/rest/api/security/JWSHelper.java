//package rest.api.security;
//
//import com.nimbusds.jose.*;
//import com.nimbusds.jose.crypto.MACSigner;
//import com.nimbusds.jose.crypto.MACVerifier;
//
//import java.security.SecureRandom;
//import java.text.ParseException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class JWSHelper {
//    private static final byte[] SECRET = new byte[32];
//
//    private static final Logger LOG = Logger.getLogger(JWSHelper.class.getName());
//
//
//    static {
//        // todo dodać do konfiguracji
//        SecureRandom random = new SecureRandom();
//        random.nextBytes(SECRET);
//    }
//
//    public static String sign(String payload) {
//        JWSSigner signer = null;
//        try {
//            signer = new MACSigner(SECRET);
//        } catch (KeyLengthException e) {
//            LOG.log(Level.SEVERE, "Wrong SECRET key length.");
//            e.printStackTrace();
//        }
//        JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(payload));
//        try {
//            jwsObject.sign(signer);
//        } catch (JOSEException e) {
//            LOG.log(Level.INFO, "Couldn't sign jws object.");
//            e.printStackTrace();
//        }
//        return jwsObject.serialize();
//    }
//
//    public static boolean verify(String payload, String signed) {
//        JWSObject jwsObject = null;
//        try {
//            jwsObject = JWSObject.parse(signed);
//        } catch (ParseException e) {
//            LOG.log(Level.INFO, "Couldn't parse string to jws object.");
//            return false;
//        }
//        try {
//            JWSVerifier verifier = new MACVerifier(SECRET);
//            if(!jwsObject.verify(verifier)) {
//                return false;
//            }
//        } catch (JOSEException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//        return payload.equals(jwsObject.getPayload().toString());
//    }
//}
