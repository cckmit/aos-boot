package ink.aos.boot.security.authentication;

import ink.aos.boot.security.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Slf4j
@Getter
public class UserAuthenticationToken extends AbstractAuthenticationToken {

    private final User user;
    private final String accessToken;
    private final long expiresIn;

    public UserAuthenticationToken(User user, String accessToken, long expiresIn) {
        super(user.getAuthorities());
        this.user = user;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

}
