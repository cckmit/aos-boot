package ink.aos.boot.db.config;

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
public class TombstoneJpaAspect {
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
        org.hibernate.Filter filter = entityManager.unwrap(Session.class).enableFilter(ApDBConstants.TOMBSTONE_FILTER);
        filter.setParameter("deleted", false);
        filter.validate();
        return pjp.proceed();
    }

}
