package ink.aos.boot.db.config;

import ink.aos.boot.db.multitenancy.aspects.IgnoreMultiTenancyDiscriminatorAspect;
import ink.aos.boot.db.multitenancy.aspects.MultiTenancyDiscriminatorAspect;
import ink.aos.boot.db.multitenancy.aspects.MultiTenancyDiscriminatorCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 3/7/21
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
@Configuration
@EnableAspectJAutoProxy
@ConditionalOnProperty(name = {"aos.multi-tenancy.enable"}, havingValue = "true")
public class MultiTenancyConfiguration {

    @Bean
    @Conditional(MultiTenancyDiscriminatorCondition.class)
    public MultiTenancyDiscriminatorAspect multiTenancyDiscriminatorAspect() {
        return new MultiTenancyDiscriminatorAspect();
    }

    @Bean
    @Conditional(MultiTenancyDiscriminatorCondition.class)
    public IgnoreMultiTenancyDiscriminatorAspect ignoreMultiTenancyDiscriminatorAspect() {
        return new IgnoreMultiTenancyDiscriminatorAspect();
    }

}
