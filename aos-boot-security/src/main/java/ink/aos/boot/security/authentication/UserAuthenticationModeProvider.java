package ink.aos.boot.security.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public abstract class UserAuthenticationModeProvider implements AuthenticationProvider {

    public abstract UserAuthenticationToken userAuthenticate(Authentication authentication) throws AuthenticationException;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return userAuthenticate(authentication);
    }
}
