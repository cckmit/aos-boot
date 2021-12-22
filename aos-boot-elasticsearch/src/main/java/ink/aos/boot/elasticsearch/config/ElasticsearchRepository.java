package ink.aos.boot.elasticsearch.config;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 3/24/21
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ElasticsearchRepository {

    @AliasFor(annotation = Component.class)
    String value() default "";
    
}
