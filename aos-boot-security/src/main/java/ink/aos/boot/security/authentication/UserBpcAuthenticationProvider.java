package ink.aos.boot.security.authentication;

import ink.aos.boot.security.User;
import ink.aos.boot.security.config.SecurityUserAuthProperties;
import ink.aos.boot.security.service.BlockPuzzleCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserBpcAuthenticationProvider extends UserAuthenticationModeProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityUserAuthProperties securityUserAuthProperties;

    @Autowired
    private BlockPuzzleCaptchaService blockPuzzleCaptchaService;

    @Override
    public UserAuthenticationToken userAuthenticate(Authentication authentication) throws AuthenticationException {
        UserBpcAuthenticationToken userBpcAuthenticationToken = (UserBpcAuthenticationToken) authentication;

        User user = (User) userDetailsService.loadUserByUsername(userBpcAuthenticationToken.getUsername());//从库里查找用户的代码略;
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        if (!passwordEncoder.matches(userBpcAuthenticationToken.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("验证失败");
        }

        try {
            blockPuzzleCaptchaService.verification(userBpcAuthenticationToken.getBpcToken());
        } catch (Exception e) {
            throw new BadCredentialsException("验证码校验失败:" + e.getMessage());
        }

        UserAuthenticationToken userAuthenticationToken = new UserAuthenticationToken(user, UserAuthenticationTokenUtil.createToken(), securityUserAuthProperties.getExpiresIn());
        return userAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UserBpcAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
