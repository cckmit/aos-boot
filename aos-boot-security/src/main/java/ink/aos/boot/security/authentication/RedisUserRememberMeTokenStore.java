package ink.aos.boot.security.authentication;

import ink.aos.boot.security.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisUserRememberMeTokenStore implements UserRememberMeTokenStore {

    private final String REDIS_KEY_PRE = "aos_auth:user_remember_me:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public UserRememberMeToken findByAccessToken(String token) {
        RedisUserRememberMeTokenStore.UserRememberMeTokenBean bean = (RedisUserRememberMeTokenStore.UserRememberMeTokenBean) redisTemplate.boundValueOps(key(token)).get();
        if (bean != null) {
            return bean.to();
        }
        return null;
    }

    @Override
    public void save(UserRememberMeToken token) {
        String accessToken = token.getAccessToken();
        String key = key(accessToken);
        redisTemplate.boundValueOps(key).set(RedisUserRememberMeTokenStore.UserRememberMeTokenBean.create(token), token.getExpiresIn(), TimeUnit.SECONDS);
    }

    @Override
    public void delay(UserRememberMeToken token) {
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
    private static class UserRememberMeTokenBean {

        private User user;
        private String accessToken;
        private long expiresIn;


        private static RedisUserRememberMeTokenStore.UserRememberMeTokenBean create(UserRememberMeToken authenticationToken) {
            return RedisUserRememberMeTokenStore.UserRememberMeTokenBean.builder()
                    .user(authenticationToken.getUser())
                    .expiresIn(authenticationToken.getExpiresIn())
                    .accessToken(authenticationToken.getAccessToken())
                    .build();
        }

        private UserRememberMeToken to() {
            return new UserRememberMeToken(user, accessToken, expiresIn);
        }

    }

}
