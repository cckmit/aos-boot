package ink.aos.boot.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@ConditionalOnMissingBean(DiySecurityConfig.class)
public class DiySecurityConfig {

    public void configure(HttpSecurity http) throws Exception {

    }

}
