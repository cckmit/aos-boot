package ink.aos.boot.security.authentication;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.util.Assert;

@Getter
public class UserBpcAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 550L;
    private final String username;
    private final String password;
    private final String bpcToken;

    public UserBpcAuthenticationToken(String username, String password, String bpcToken) {
        super(null);
        this.username = username;
        this.password = password;
        this.bpcToken = bpcToken;
        this.setAuthenticated(false);
    }

    public Object getCredentials() {
        return this.password;
    }

    public Object getPrincipal() {
        return this.username;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

}
