package ink.aos.boot.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AosMultiTenancyProperties.class)
public class CoreConfiguration {
}
