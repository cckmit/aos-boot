package ink.aos.boot.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aos.security.authorized-url")
public class SecurityAuthorizedUrlProperties {

    private String[] authenticateds;
    private String[] permitAlls;
}
