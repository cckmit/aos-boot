package ink.aos.boot.security.authentication;

import java.util.UUID;

public class UserAuthenticationTokenUtil {

    public static String createToken() {
        return UUID.randomUUID().toString();
    }

}
