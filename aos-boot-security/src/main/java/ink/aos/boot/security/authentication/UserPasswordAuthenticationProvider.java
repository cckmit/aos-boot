package ink.aos.boot.security.authentication;

import ink.aos.boot.security.User;
import ink.aos.boot.security.config.SecurityUserAuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserPasswordAuthenticationProvider extends UserAuthenticationModeProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityUserAuthProperties securityUserAuthProperties;

    @Override
    public UserAuthenticationToken userAuthenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal(); // Principal 主体，一般指用户名
        String password = (String) authentication.getCredentials(); //Credentials 网络凭证,一般指密码

        User user = (User) userDetailsService.loadUserByUsername(username);//从库里查找用户的代码略;

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("验证失败");
        }

        UserAuthenticationToken userAuthenticationToken = new UserAuthenticationToken(user, UserAuthenticationTokenUtil.createToken(), securityUserAuthProperties.getExpiresIn());

        return userAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UserPasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
