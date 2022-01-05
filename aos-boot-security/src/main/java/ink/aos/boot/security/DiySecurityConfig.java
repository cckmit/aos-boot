package ink.aos.boot.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@ConditionalOnMissingBean(DiySecurityConfig.class)
public class DiySecurityConfig {

    private SecurityConfiguration securityConfiguration;

    public DiySecurityConfig setSecurityConfiguration(SecurityConfiguration securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
        return this;
    }

    public void configure(HttpSecurity http) throws Exception {

    }

    public AuthenticationManager authenticationManager() throws Exception {
        return securityConfiguration.authenticationManager();
    }

    public void configure(AuthenticationManagerBuilder auth) {

    }
}
