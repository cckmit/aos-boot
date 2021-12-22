package ink.aos.boot.db.multitenancy.aspects;

import ink.aos.boot.db.multitenancy.IgnoreMultiTenancy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class IgnoreMultiTenancyDiscriminatorAspect {

    @Pointcut(value = "" +
            "@within(ink.aos.boot.db.multitenancy.TenantIgnore) || " +
            "@annotation(ink.aos.boot.db.multitenancy.TenantIgnore)" +
            "")
    void ignore() {
    }

    @Around("execution(public * *(..)) && ignore()")
    public Object aroundExecution(ProceedingJoinPoint pjp) throws Throwable {
        IgnoreMultiTenancy.setIgnore(true);
        return pjp.proceed();
    }

}