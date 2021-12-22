package ink.aos.boot.web.config;

import ink.aos.boot.web.logging.WebLoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class WebLoggingAspectConfiguration {

    @Bean
    @Profile({"dev"})
    public WebLoggingAspect webLoggingAspect(Environment env) {
        return new WebLoggingAspect(env);
    }
}
