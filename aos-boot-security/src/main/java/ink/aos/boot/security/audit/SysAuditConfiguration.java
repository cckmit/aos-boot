package ink.aos.boot.security.audit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 4/27/21
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
@Configuration
@EnableAspectJAutoProxy
public class SysAuditConfiguration {

    @Bean
    public SysAuditAspect sysAuditAspect() {
        return new SysAuditAspect();
    }

    @Bean
    @ConditionalOnProperty(name = "aos.security.sys-audit.enable", havingValue = "false")
    public SysAuditHandle sysAuditService() {
        return new SysAuditHandleImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public SysAuditHandle UaaSysAuditService() {
        return new UaaSysAuditHandleImpl();
    }

}
