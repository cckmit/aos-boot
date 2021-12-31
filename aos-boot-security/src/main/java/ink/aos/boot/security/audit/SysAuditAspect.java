package ink.aos.boot.security.audit;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import ink.aos.boot.security.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
public class SysAuditAspect {

    @Autowired
    private SysAuditHandle sysAuditHandle;

    @Value("${spring.application.name}")
    private String serviceId;

    @Pointcut("@annotation(ink.aos.boot.security.audit.AnnoSysAudit)")
    public void audit() {
    }

    @AfterReturning("audit()")
    public void exec(JoinPoint joinPoint) {
        SecurityUtils.getCurrentUserId().ifPresent(userId -> {
            exec_(userId, joinPoint);
        });
    }

    public void exec_(String userId, JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        AnnoSysAudit annoSysAudit = giveController(joinPoint);
        SysAudit sysAudit = new SysAudit();
        sysAudit.setUserId(userId);
        sysAudit.setServiceId(serviceId);
        sysAudit.setModule(annoSysAudit.module());
        sysAudit.setIp(getIpAddress(request));
        sysAudit.setDescription(annoSysAudit.description());

        {
            String agent = request.getHeader("User-Agent");

            //解析agent字符串
            UserAgent userAgent = UserAgent.parseUserAgentString(agent);

            //获取浏览器对象
            Browser browser = userAgent.getBrowser();

            //获取操作系统对象
            OperatingSystem operatingSystem = userAgent.getOperatingSystem();

            if (browser != null) {
                sysAudit.setBrowserName(browser.getName());
                sysAudit.setBrowserType(browser.getBrowserType().getName());
                sysAudit.setBrowserGroup(browser.getGroup().getName());
                sysAudit.setBrowserManufacturer(browser.getManufacturer().getName());
                sysAudit.setBrowserRenderingEngine(browser.getRenderingEngine().getName());
            }
            if (operatingSystem != null) {
                sysAudit.setOsName(operatingSystem.getName());
                sysAudit.setOsDeviceType(operatingSystem.getDeviceType().getName());
                sysAudit.setOsGroup(operatingSystem.getGroup().getName());
                sysAudit.setOsManufacturer(operatingSystem.getManufacturer().getName());
            }
            if (userAgent.getBrowserVersion() != null) {
                sysAudit.setBrowserVersion(userAgent.getBrowserVersion().getVersion());
            }
        }

        sysAudit.setClassName(joinPoint.getSignature().getDeclaringTypeName());
        sysAudit.setMethodName(joinPoint.getSignature().getName());
        if (annoSysAudit.includeParam()) sysAudit.setParam(Arrays.toString(joinPoint.getArgs()));

        sysAuditHandle.save(sysAudit);

    }

    private static AnnoSysAudit giveController(JoinPoint joinPoint) {
          /*
           * JoinPoint可以获取什么：
            l java.lang.Object[] getArgs()：获取连接点方法运行时的入参列表；
            l Signature getSignature() ：获取连接点的方法签名对象；
            l java.lang.Object getTarget() ：获取连接点所在的目标对象；
            l java.lang.Object getThis() ：获取代理对象本身；
           */
        Signature signature = joinPoint.getSignature();

           /*
            * MethodSignature可以获取什么：
               Class getReturnType(); 获取方法的返回类型
              Method getMethod(); 获取方法本身
            */
        MethodSignature methodSignature = (MethodSignature) signature;
        /**
         * Method可以获取方法上的各种信息 比如方法名称 方法参数 注解 参数上的注解等。
         */
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(AnnoSysAudit.class);
        }
        return null;
    }

    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
