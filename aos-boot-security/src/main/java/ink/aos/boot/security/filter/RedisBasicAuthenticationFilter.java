package ink.aos.boot.security.filter;

import ink.aos.boot.security.authentication.RedisBearerAuthenticationConverter;
import ink.aos.boot.security.authentication.UserAuthenticationToken;
import ink.aos.boot.security.authentication.UserAuthenticationTokenStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RedisBasicAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisBearerAuthenticationConverter authenticationConverter;
    @Autowired
    private UserAuthenticationTokenStore userAuthenticationTokenStore;

    public RedisBasicAuthenticationFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        try {
            UserAuthenticationToken authentication = (UserAuthenticationToken) authenticationConverter.convert(request);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                userAuthenticationTokenStore.delay(authentication);
            }
        } catch (Exception e) {
            logger.warn(e.getClass().getName() + ":" + e.getMessage());
        }
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }

}
