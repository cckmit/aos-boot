package ink.aos.boot.elasticsearch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 3/27/21
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({
        ElasticsearchConfiguration.class,
})
@Configuration
public @interface EnableElasticsearch {
}
