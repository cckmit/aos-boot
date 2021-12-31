package ink.aos.boot.security.audit;

import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 4/27/21
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Configuration
public @interface AnnoSysAudit {

    /**
     * 模块名
     *
     * @return
     */
    String module();

    String description() default "no description";

    boolean includeParam() default true;

}
