package ink.aos.boot.security.filter;

import ink.aos.boot.security.authentication.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderNotFoundException;
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

    @Autowired
    private DefaultAuthenticationEventPublisher eventPublisher;

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

        Authentication authentication = null;
        try {
            switch (mode) {
                case "password":
                    authentication = new UserPasswordAuthenticationToken(username, password);
                    break;
                case "bpc":
                    String bpcToken = get(request, "bpc-token");
                    authentication = new UserBpcAuthenticationToken(username, password, bpcToken);
                    break;
                case "sms":
                    String smsCode = get(request, "sms-code");
                    authentication = new UserSmsAuthenticationToken(username, smsCode);
                    break;
                default:
                    throw new ProviderNotFoundException("not mode");
            }
            authentication = getAuthenticationManager().authenticate(authentication);
            tokenStore.save((UserAuthenticationToken) authentication);
            return authentication;
        } catch (ProviderNotFoundException e) {
            throw e;
        } catch (AuthenticationException e) {
            eventPublisher.publishAuthenticationFailure(e, authentication);
            throw e;
        }
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
