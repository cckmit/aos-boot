package ink.aos.boot.security.filter;

import ink.aos.boot.security.authentication.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login",
            "POST");

    @Autowired
    private UserAuthenticationSuccessSendHandler userAuthenticationSuccessSendHandler;

    @Autowired
    private UserAuthenticationTokenStore tokenStore;

    public UserAuthenticationFilter(AuthenticationManager authenticationManager,
                                    RememberMeServices rememberMeServices) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
        setRememberMeServices(rememberMeServices);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = get(request, "username");
        String password = get(request, "password");
        String mode = get(request, "mode");
        String bpcToken = get(request, "bpc-token");

        Authentication authentication;
        switch (mode) {
            case "password":
                authentication = new UserPasswordAuthenticationToken(username, password);
                break;
            case "bpc":
                authentication = new UserBpcAuthenticationToken(username, password, bpcToken);
                break;
            default:
                throw new BadCredentialsException("not mode");
        }
        authentication = getAuthenticationManager().authenticate(authentication);
        tokenStore.save((UserAuthenticationToken) authentication);
        return authentication;
    }

    private String get(HttpServletRequest request, String key) {
        String v = request.getParameter(key);
        v = (v != null) ? v : "";
        v = v.trim();
        return v;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
//        setAuthenticationFailureHandler((request, response, exception) -> {
//            if (exception instanceof BadCredentialsException) {
//                exception = new BadCredentialsException(exception.getMessage(), new BadCredentialsException(""));
//            }
//            authenticationEntryPoint.commence(request, response, exception);
//        });
        setAuthenticationSuccessHandler(userAuthenticationSuccessSendHandler);
    }

}
