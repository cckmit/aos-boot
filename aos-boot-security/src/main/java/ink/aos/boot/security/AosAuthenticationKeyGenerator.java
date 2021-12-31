package ink.aos.boot.security;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;

import java.util.UUID;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 12/21/20
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
public class AosAuthenticationKeyGenerator implements AuthenticationKeyGenerator {

//    private static final String CLIENT_ID = "client_id";
//
//    private static final String SCOPE = "scope";
//
//    private static final String USERNAME = "username";

    public String extractKey(OAuth2Authentication authentication) {
        return UUID.randomUUID().toString();
//        Map<String, String> values = new LinkedHashMap<String, String>();
//        OAuth2Request authorizationRequest = authentication.getOAuth2Request();
//        if (!authentication.isClientOnly()) {
//            values.put(USERNAME, authentication.getName());
//        }
//        values.put(CLIENT_ID, authorizationRequest.getClientId());
//        if (authorizationRequest.getScope() != null) {
//            values.put(SCOPE, OAuth2Utils.formatParameterList(new TreeSet<String>(authorizationRequest.getScope())));
//        }
//        return generateKey(values);
    }

//    protected String generateKey(Map<String, String> values) {
//        MessageDigest digest;
//        try {
//            digest = MessageDigest.getInstance("MD5");
//            byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
//            return String.format("%032x", new BigInteger(1, bytes));
//        } catch (NoSuchAlgorithmException nsae) {
//            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).", nsae);
//        } catch (UnsupportedEncodingException uee) {
//            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).", uee);
//        }
//    }
}
