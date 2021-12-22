package ink.aos.boot.db.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 2019-08-21
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({
        DatabaseConfiguration.class,
        DBJacksonConfiguration.class,
        MultiTenancyConfiguration.class,
})
@Configuration
public @interface EnableSaasBootDB {
}
