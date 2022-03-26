package ink.aos.boot.security.authentication;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.util.Assert;

@Getter
public class UserSmsAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 550L;
    private final String username;
    private final String smsCode;

    public UserSmsAuthenticationToken(String username, String smsCode) {
        super(null);
        this.username = username;
        this.smsCode = smsCode;
        this.setAuthenticated(false);
    }

    public Object getCredentials() {
        return this.smsCode;
    }

    public Object getPrincipal() {
        return this.username;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

}
