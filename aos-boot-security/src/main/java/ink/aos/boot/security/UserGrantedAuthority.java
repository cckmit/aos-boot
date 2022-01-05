package ink.aos.boot.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserGrantedAuthority implements GrantedAuthority {

    private String authority;

}
