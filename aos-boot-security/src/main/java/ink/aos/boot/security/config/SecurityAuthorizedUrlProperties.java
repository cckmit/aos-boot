package ink.aos.boot.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 6/18/21
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
@Data
@ConfigurationProperties(prefix = "aos.security.authorized-url")
public class SecurityAuthorizedUrlProperties {

    private String[] authenticateds;
    private String[] permitAlls;
}
