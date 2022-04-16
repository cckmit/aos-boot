package ink.aos.boot.security.authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserAuthenticationClientStore {

    public static final String SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY = "remember_me";
    private static final String ACCESS_TOKEN_COOKIE_KEY = "access_token";

    public static void setAccessTokenCookie(String token, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(ACCESS_TOKEN_COOKIE_KEY, token);
        cookie.setPath(getCookiePath(request));
        cookie.setSecure(request.isSecure());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        response.addHeader(ACCESS_TOKEN_COOKIE_KEY, token);
    }

    public static String extractRememberMeCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if ((cookies == null) || (cookies.length == 0)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static void setRememberMeCookie(String token, long maxAge, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, token);
        cookie.setMaxAge((int) maxAge);
        cookie.setPath(getCookiePath(request));
        if (maxAge < 1) {
            cookie.setVersion(1);
        }
        cookie.setSecure(request.isSecure());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        response.addHeader(SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, token);
    }

    private static String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return (contextPath.length() > 0) ? contextPath : "/";
    }

}
