package ink.aos.boot.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationSuccessSendHandler extends UserAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);
        if (UserAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) authentication;
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
