package ink.aos.boot.security.authentication;

import ink.aos.boot.security.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisUserAuthenticationTokenStore implements UserAuthenticationTokenStore {

    private final String REDIS_KEY_PRE = "aos_auth:user_auth:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public UserAuthenticationToken findByAccessToken(String token) {
        UserAuthenticationTokenBean bean = (UserAuthenticationTokenBean) redisTemplate.boundValueOps(key(token)).get();
        if (bean != null) {
            return bean.to();
        }
        return null;
    }

    @Override
    public void save(UserAuthenticationToken token) {
        String accessToken = token.getAccessToken();
        String key = key(accessToken);
        redisTemplate.boundValueOps(key).set(UserAuthenticationTokenBean.create(token), token.getExpiresIn(), TimeUnit.SECONDS);
    }

    @Override
    public void delay(UserAuthenticationToken token) {
        String accessToken = token.getAccessToken();
        String key = key(accessToken);
        redisTemplate.boundValueOps(key).expire(token.getExpiresIn(), TimeUnit.SECONDS);
    }

    @Override
    public void remove(String token) {
        redisTemplate.delete(key(token));
    }

    public String key(String key) {
        return REDIS_KEY_PRE + key;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    private static class UserAuthenticationTokenBean {

        private User user;
        private String accessToken;
        private long expiresIn;


        private static UserAuthenticationTokenBean create(UserAuthenticationToken authenticationToken) {
            return UserAuthenticationTokenBean.builder()
                    .user(authenticationToken.getUser())
                    .expiresIn(authenticationToken.getExpiresIn())
                    .accessToken(authenticationToken.getAccessToken())
                    .build();
        }

        private UserAuthenticationToken to() {
            return new UserAuthenticationToken(user, accessToken, expiresIn);
        }

    }
    
}
