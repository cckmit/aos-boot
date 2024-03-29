package ink.aos.boot.db.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@Configuration
@EnableJpaRepositories(value = "ink.aos",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Repository.class)}
)
@EnableTransactionManagement
public class DatabaseConfiguration {

    public DatabaseConfiguration() {
        log.debug("boot db config success");
    }

//    @Bean
//    public TombstoneJpaAspect tombstoneJpaAspect() {
//        return new TombstoneJpaAspect();
//    }
}
