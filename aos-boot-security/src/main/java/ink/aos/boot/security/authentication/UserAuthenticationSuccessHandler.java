package ink.aos.boot.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (UserAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) authentication;

            UserAuthenticationClientStore.setAccessTokenCookie(userAuthenticationToken.getAccessToken(), request, response);

            if (StringUtils.isNotBlank(userAuthenticationToken.getRememberMeToken())) {
                UserAuthenticationClientStore.setRememberMeCookie(userAuthenticationToken.getRememberMeToken(), userAuthenticationToken.getRememberMeExpiresIn(), request, response);
            }

            UserAuthenticationResp resp = UserAuthenticationResp.builder()
                    .accessToken(userAuthenticationToken.getAccessToken())
                    .expiresIn(userAuthenticationToken.getExpiresIn())
                    .rememberMeToken(userAuthenticationToken.getRememberMeToken())
                    .rememberMeExpiresIn(userAuthenticationToken.getRememberMeExpiresIn())
                    .build();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getOutputStream().write(objectMapper.writeValueAsBytes(resp));

        }
    }

}
