package ink.aos.boot.security.authentication;

import ink.aos.boot.security.User;
import ink.aos.boot.security.config.SecurityUserAuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserSmsAuthenticationProvider extends UserAuthenticationModeProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityUserAuthProperties securityUserAuthProperties;

    @Autowired
    private UserSmsAuthService userSmsAuthService;

    @Override
    public UserAuthenticationToken userAuthenticate(Authentication authentication) throws AuthenticationException {
        UserSmsAuthenticationToken userSmsAuthenticationToken = (UserSmsAuthenticationToken) authentication;

        User user = (User) userDetailsService.loadUserByUsername(userSmsAuthenticationToken.getUsername());//从库里查找用户的代码略;
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        String code = userSmsAuthenticationToken.getSmsCode();
        try {
            userSmsAuthService.validate(user, code);
        } catch (Exception e) {
            throw new BadCredentialsException("验证码校验失败:" + e.getMessage());
        }

        return new UserAuthenticationToken(user, UserAuthenticationTokenUtil.createToken(), securityUserAuthProperties.getExpiresIn());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UserSmsAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
