package ink.aos.boot.db.multitenancy.aspects;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.MultiTenancyStrategy;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Slf4j
public class MultiTenancyDiscriminatorCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String mode = context.getEnvironment().getProperty("spring.jpa.properties.hibernate.multiTenancy");
        if (MultiTenancyStrategy.DISCRIMINATOR.name().equals(mode)) {
            return true;
        }
        return false;
    }
}