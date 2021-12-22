package ink.aos.boot.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(value = "aos.multi-tenancy")
public class AosMultiTenancyProperties {
    private boolean enable = false;
}
