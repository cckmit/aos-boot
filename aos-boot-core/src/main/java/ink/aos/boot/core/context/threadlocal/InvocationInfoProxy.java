package ink.aos.boot.core.context.threadlocal;

public class InvocationInfoProxy {

    public static String TENANT_ID_HEADER = "X-tenant-id";
    public static String USER_SCOPE_HEADER = "X-user-scope";

    private static final ThreadLocal<InvocationInfo> threadLocal = ThreadLocal.withInitial(() -> new InvocationInfo());

    public static void reset() {
        threadLocal.remove();
    }

    public static String getTenantId() {
        return threadLocal.get().tenantId;
    }

    public static void setTenantId(String tenantId) {
        threadLocal.get().tenantId = tenantId;
    }

    public static void setUserScope(String userScope) {
        threadLocal.get().userScope = userScope;
    }

    public static String getUserScope() {
        return threadLocal.get().userScope;
    }

}

class InvocationInfo {

    String tenantId;

    String userScope;

}