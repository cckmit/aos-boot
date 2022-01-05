package ink.aos.boot.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserAuthenticationTokenStore userAuthenticationTokenStore;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) authentication;
        userAuthenticationTokenStore.delay(userAuthenticationToken);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
