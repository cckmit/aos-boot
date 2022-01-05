package ink.aos.boot.web.config;

import ink.aos.boot.web.listener.TenantReqListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRequestListener;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 2/20/21
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
@Configuration
public class WebListenerConfiguration {

    @Bean
    @ConditionalOnMissingBean(TenantReqListener.class)
    public ServletListenerRegistrationBean<ServletRequestListener> tenantReqFilter() {
        ServletListenerRegistrationBean<ServletRequestListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<>();
        servletListenerRegistrationBean.setListener(new TenantReqListener());
        return servletListenerRegistrationBean;
    }
    
}
