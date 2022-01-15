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

    private String rememberMeToken;
    private Long rememberMeExpiresIn;

    public UserAuthenticationToken(User user,
                                   String accessToken,
                                   long expiresIn) {
        super(user.getAuthorities());
        setAuthenticated(true);
        this.user = user;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public void setRememberMe(String rememberMeToken, long rememberMeExpiresIn) {
        this.rememberMeToken = rememberMeToken;
        this.rememberMeExpiresIn = rememberMeExpiresIn;
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
