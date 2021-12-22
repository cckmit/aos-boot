package ink.aos.boot.ap.util;

import org.springframework.data.domain.ExampleMatcher;

/**
 * All rights Reserved, Designed By www.ctodb.cn
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 2018-12-09 11:06
 * @Copyright: 2018 www.ctodb.cn All rights reserved.
 */
public class ExampleUtil {

    public static ExampleMatcher ignore() {
        return ExampleMatcher.matching().withIgnoreNullValues().withIgnorePaths("createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate", "beanMap");
    }

}
