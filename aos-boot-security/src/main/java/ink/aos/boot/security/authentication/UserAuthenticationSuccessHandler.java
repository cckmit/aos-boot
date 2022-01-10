package ink.aos.boot.security.authentication;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (UserAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) authentication;

            UserAuthenticationClientStore.setAccessTokenCookie(userAuthenticationToken.getAccessToken(), request, response);

            if (StringUtils.isNotBlank(userAuthenticationToken.getRememberMeToken())) {
                UserAuthenticationClientStore.setRememberMeCookie(userAuthenticationToken.getRememberMeToken(), userAuthenticationToken.getRememberMeExpiresIn(), request, response);
            }
        }
    }

}
