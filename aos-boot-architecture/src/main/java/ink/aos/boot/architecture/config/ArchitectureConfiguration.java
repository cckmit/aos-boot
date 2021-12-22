package ink.aos.boot.architecture.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ArchitectureConfiguration {

    public ArchitectureConfiguration() {
        log.debug("Architecture config success");
    }

}
