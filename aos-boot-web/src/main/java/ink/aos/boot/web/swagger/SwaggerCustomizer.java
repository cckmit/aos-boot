package ink.aos.boot.web.swagger;

import springfox.documentation.spring.web.plugins.Docket;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 12/23/20
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
@FunctionalInterface
public interface SwaggerCustomizer {

    /**
     * Customize the Springfox Docket.
     *
     * @param docket the Docket to customize
     */
    void customize(Docket docket);
}

