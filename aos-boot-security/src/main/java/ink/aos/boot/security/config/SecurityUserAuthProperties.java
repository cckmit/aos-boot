package ink.aos.boot.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "aos.security.user-auth")
public class SecurityUserAuthProperties {

    private long expiresIn = 30 * 60;

    private long rememberMeExpiresIn = 7 * 24 * 60 * 60;

    private LoginSms loginSms = new LoginSms();

    @Data
    public static class LoginSms {
        private int length = 4;
        private Duration expire = Duration.parse("PT60S");
    }

}
