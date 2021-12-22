package ink.aos.boot.db.multitenancy;

import java.lang.annotation.*;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 3/8/21
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TenantIgnore {

}
