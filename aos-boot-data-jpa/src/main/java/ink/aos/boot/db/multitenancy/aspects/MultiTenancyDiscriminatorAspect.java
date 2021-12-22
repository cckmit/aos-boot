package ink.aos.boot.db.multitenancy.aspects;

import ink.aos.boot.core.context.threadlocal.InvocationInfoProxy;
import ink.aos.boot.db.config.ApDBConstants;
import ink.aos.boot.db.multitenancy.IgnoreMultiTenancy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Aspect
public class MultiTenancyDiscriminatorAspect {

    @Pointcut(value = "@within(org.springframework.stereotype.Repository)"
            + " || @within(org.springframework.transaction.annotation.Transactional)"
            + " || @annotation(org.springframework.transaction.annotation.Transactional)")
    void hasRepositoryAnnotation() {
    }

    @Pointcut(value = "hasRepositoryAnnotation()")
    void hasMultiTenantAnnotation() {
    }

    @PersistenceContext
    public EntityManager entityManager;

    @Around("execution(public * *(..)) && hasMultiTenantAnnotation()")
    public Object aroundExecution(ProceedingJoinPoint pjp) throws Throwable {
        if (!IgnoreMultiTenancy.ignore()) {
            String tenantId = InvocationInfoProxy.getTenantId();
            org.hibernate.Filter filter = entityManager.unwrap(Session.class).enableFilter(ApDBConstants.TENANT_FILTER);
            filter.setParameter("tenantId", tenantId);
            filter.validate();
        }
        return pjp.proceed();
    }

}