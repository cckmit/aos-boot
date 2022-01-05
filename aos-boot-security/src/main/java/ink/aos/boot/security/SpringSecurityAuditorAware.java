package ink.aos.boot.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementation of {@link AuditorAware} based on Spring Security.
 */
@Component
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserId().orElse("system"));
    }

}
