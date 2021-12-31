package ink.aos.boot.security;

import ink.aos.boot.security.config.SecurityAuthorizedUrlProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
//@ConditionalOnMissingBean(UaaServicePoint.class)
@EnableConfigurationProperties(SecurityAuthorizedUrlProperties.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final SecurityProblemSupport problemSupport;

    private final SecurityAuthorizedUrlProperties securityAuthorizedUrlProperties;

    private final DiySecurityConfig diySecurityConfig;

    public SecurityConfiguration(SecurityProblemSupport problemSupport,
                                 SecurityAuthorizedUrlProperties securityAuthorizedUrlProperties,
                                 DiySecurityConfig diySecurityConfig) {
        this.problemSupport = problemSupport;
        this.securityAuthorizedUrlProperties = securityAuthorizedUrlProperties;
        this.diySecurityConfig = diySecurityConfig;
        log.debug("SecurityConfiguration ------------");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .accessDeniedHandler(problemSupport)
                .authenticationEntryPoint(problemSupport)
                .and()
                .csrf().disable()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;

        authorizedUrl(http.authorizeRequests());
        diySecurityConfig.configure(http);
    }

    private void authorizedUrl(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        if (securityAuthorizedUrlProperties.getPermitAlls() != null) {
            log.debug("security PermitAlls : {}", securityAuthorizedUrlProperties.getPermitAlls());
            registry.antMatchers(securityAuthorizedUrlProperties.getPermitAlls()).permitAll();
        }
        if (securityAuthorizedUrlProperties.getAuthenticateds() != null) {
            log.debug("security Authenticateds : {}", securityAuthorizedUrlProperties.getAuthenticateds());
            registry.antMatchers(securityAuthorizedUrlProperties.getAuthenticateds()).authenticated();
        }
        registry.antMatchers("/ap/**").authenticated()
                .antMatchers("/management/health").permitAll()
                .antMatchers("/management/info").permitAll()
                .antMatchers("/management/prometheus").permitAll()
                .antMatchers("/swagger-resources").authenticated()
                .antMatchers("/swagger-resources/**").authenticated()
                .antMatchers("/v2/api-docs").authenticated()
                .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN);
    }

}
