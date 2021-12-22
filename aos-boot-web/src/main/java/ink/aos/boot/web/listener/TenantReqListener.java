package ink.aos.boot.web.listener;

import ink.aos.boot.core.context.threadlocal.InvocationInfoProxy;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 2/20/21
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
@Slf4j
public class TenantReqListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        if (HttpServletRequest.class.isInstance(sre.getServletRequest())) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) sre.getServletRequest();
            String tenantId = httpServletRequest.getHeader(InvocationInfoProxy.TENANT_ID_HEADER);
            String userScope = httpServletRequest.getHeader(InvocationInfoProxy.USER_SCOPE_HEADER);
            InvocationInfoProxy.setTenantId(tenantId);
            InvocationInfoProxy.setUserScope(userScope);
        }
    }
}
