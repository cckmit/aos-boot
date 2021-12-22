package ink.aos.boot.web.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 2020-04-27
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
@Data
@ConfigurationProperties(prefix = "aos.swagger")
public class SwaggerProperties {

    private String title = "swagger ap";

    private String description = "description";

    private String version = "0.0.1";

    private String termsOfServiceUrl = null;

    private String contactName = null;

    private String contactUrl = null;

    private String contactEmail = null;

    private String license = null;

    private String licenseUrl = null;

    private String defaultIncludePattern = "/ap/*";

    private String host = null;

    private String[] protocols = {};

    private boolean useDefaultResponseMessages = true;

}
