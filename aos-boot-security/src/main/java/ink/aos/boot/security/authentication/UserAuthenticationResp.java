package ink.aos.boot.security.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserAuthenticationResp {

    private final String accessToken;
    private final long expiresIn;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rememberMeToken;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Long rememberMeExpiresIn;

}
