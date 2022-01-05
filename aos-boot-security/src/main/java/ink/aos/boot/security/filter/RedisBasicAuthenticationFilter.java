package ink.aos.boot.security.filter;

import ink.aos.boot.security.authentication.RedisBearerAuthenticationConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RedisBasicAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private RedisBearerAuthenticationConverter authenticationConverter;

    public RedisBasicAuthenticationFilter() {
        super(null);
    }

    public RedisBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        try {
            Authentication authentication = authenticationConverter.convert(request);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        chain.doFilter(request, response);
    }

}
