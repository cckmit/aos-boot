package ink.aos.boot.db.multitenancy;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 4/2/21
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
public class IgnoreMultiTenancy {

    private static final ThreadLocal<Boolean> IGNORE_THREADLOCAL = new ThreadLocal<>();

    public static boolean ignore() {
        Boolean b = IGNORE_THREADLOCAL.get();
        if (b == null) return false;
        return b;
    }

    public static void setIgnore(boolean b) {
        IGNORE_THREADLOCAL.set(b);
    }

}
