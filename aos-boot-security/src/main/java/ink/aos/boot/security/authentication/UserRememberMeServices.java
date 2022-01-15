package ink.aos.boot.security.authentication;

import ink.aos.boot.security.config.SecurityUserAuthProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class UserRememberMeServices implements RememberMeServices, LogoutHandler {

    public static final String DEFAULT_PARAMETER = "remember-me";

    private String parameter = DEFAULT_PARAMETER;

    @Autowired
    private UserRememberMeTokenStore tokenStore;

    @Autowired
    private UserAuthenticationTokenStore authenticationTokenStore;

    @Autowired
    private SecurityUserAuthProperties securityUserAuthProperties;

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        String token = UserAuthenticationClientStore.extractRememberMeCookie(request);
        if (token == null) {
            return null;
        }
        UserRememberMeToken userRememberMeToken = tokenStore.findByAccessToken(token);
        if (userRememberMeToken == null) return null;
        UserAuthenticationToken userAuthenticationToken = new UserAuthenticationToken(userRememberMeToken.getUser(), UserAuthenticationTokenUtil.createToken(), securityUserAuthProperties.getExpiresIn());
        authenticationTokenStore.save(userAuthenticationToken);

        UserAuthenticationClientStore.setAccessTokenCookie(userAuthenticationToken.getAccessToken(), request, response);

//        if (StringUtils.isNotBlank(userAuthenticationToken.getRememberMeToken())) {
//            UserAuthenticationClientStore.setRememberMeCookie(userAuthenticationToken.getRememberMeToken(), userAuthenticationToken.getRememberMeExpiresIn(), request, response);
//        }

        return userAuthenticationToken;
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        if (!rememberMeRequested(request, parameter)) {
            return;
        }
        if (UserAuthenticationToken.class.isAssignableFrom(successfulAuthentication.getClass())) {
            UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) successfulAuthentication;
            UserRememberMeToken userRememberMeToken = new UserRememberMeToken(userAuthenticationToken.getUser(), UUID.randomUUID().toString(), securityUserAuthProperties.getRememberMeExpiresIn());
            tokenStore.save(userRememberMeToken);
            userAuthenticationToken.setRememberMe(userRememberMeToken.getAccessToken(), securityUserAuthProperties.getRememberMeExpiresIn());
        }

    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

    }

    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        String paramValue = request.getParameter(parameter);
        if (paramValue != null) {
            if (paramValue.equalsIgnoreCase("true") || paramValue.equalsIgnoreCase("on")
                    || paramValue.equalsIgnoreCase("yes") || paramValue.equals("1")) {
                return true;
            }
        }
        return false;
    }

}
