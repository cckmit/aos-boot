package ink.aos.boot.security.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 4/27/21
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
public class UaaSysAuditHandleImpl implements SysAuditHandle {

    @Autowired
    @Qualifier("loadBalancedRestTemplate")
    private RestTemplate restTemplate;

    @Async
    @Override
    public void save(SysAudit sysAudit) {
        restTemplate.postForEntity("http://uaa/sys/sys-audit", sysAudit, null);
    }

}
