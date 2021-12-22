package ink.aos.boot.db.multitenancy;

import ink.aos.boot.core.context.threadlocal.InvocationInfoProxy;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 2/17/21
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
public class MultiTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = InvocationInfoProxy.getTenantId();
        return StringUtils.isEmpty(tenantId) ? "~" : tenantId;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
