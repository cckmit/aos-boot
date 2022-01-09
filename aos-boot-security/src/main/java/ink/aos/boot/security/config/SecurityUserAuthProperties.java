package ink.aos.boot.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aos.security.user-auth")
public class SecurityUserAuthProperties {

    private long expiresIn = 30 * 60;

    private long rememberMeExpiresIn = 7 * 24 * 60 * 60;

}
