package ink.aos.boot.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;

import java.util.concurrent.TimeUnit;

public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {


    private final String REDIS_KEY_PRE = "aos_auth:oauth2_authorization:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(OAuth2Authorization authorization) {
        redisTemplate.boundValueOps(REDIS_KEY_PRE + authorization.getId()).set(authorization, authorization.getExpiresIn(), TimeUnit.SECONDS);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        redisTemplate.delete(REDIS_KEY_PRE + authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return (OAuth2Authorization) redisTemplate.boundValueOps(REDIS_KEY_PRE + id).get();
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        return null;
    }
}
