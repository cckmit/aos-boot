package ink.aos.boot.security;

import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails, CredentialsContainer {

    private String username;
    private String password;
    private String tenantId;

    private List<UserGrantedAuthority> authorities;

    private final boolean accountNonExpired = true;
    private final boolean accountNonLocked = true;
    private final boolean credentialsNonExpired = true;
    private final boolean enabled = true;

    private String id;
    private String name;
    private String email;
    private String mobile;

    @Override
    public void eraseCredentials() {
        password = null;
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof User) {
            return username.equals(((User) rhs).username);
        }
        return false;
    }

    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        return username.hashCode();
    }

}
