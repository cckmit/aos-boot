package ink.aos.boot.security.authentication;

import ink.aos.boot.core.exception.AosException;
import ink.aos.boot.security.User;
import ink.aos.boot.security.config.SecurityUserAuthProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class UserSmsAuthService {

    private final static String REDIS_UAA_CAPTCHA_CODE = "aos_uaa:login_sms_code:%s";
    private final static String REDIS_UAA_CAPTCHA_MOBILE = "aos_uaa:login_sms_mobile:";

    @Autowired
    private SecurityUserAuthProperties properties;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String create(User user, String length) {

        if (StringUtils.isBlank(user.getMobile())) {
            throw new AosException("该用户未绑定手机号");
        }

        // 生成验证码
        String code = RandomStringUtils.randomNumeric(properties.getLoginSms().getLength());

        //校验手机号是否存在
        if (StringUtils.isBlank(redisTemplate.opsForValue().get(REDIS_UAA_CAPTCHA_MOBILE + user.getMobile()))) {
            //同一个手机号 1分钟只能发送一次验证码
            redisTemplate.opsForValue().set(REDIS_UAA_CAPTCHA_MOBILE + user.getMobile(), code, 60, TimeUnit.SECONDS);
        } else {
            throw new AosException("验证码还在有效期内,请稍后重试");
        }

        // 发送
//        smsSendClient.sendSimpleByTemplate("aos", "aos-login", user.getMobile(), new String[]{code});

        // 手机号做key
        String _key = getKey(user);
        redisTemplate.boundValueOps(_key).set(code, properties.getLoginSms().getExpire());
        return code;
    }

    public void delete(User user) {
        String _key = getKey(user);
        redisTemplate.delete(_key);
    }

    private String getKey(User user) {
        return String.format(REDIS_UAA_CAPTCHA_CODE, user.getMobile());
    }

    public String getCode(User user) {
        String _key = getKey(user);
        return redisTemplate.boundValueOps(_key).get();
    }

    public void validate(User user, String code) {
        if (StringUtils.isBlank(user.getMobile())) {
            throw new AosException("该用户未绑定手机号");
        }

        String _code = getCode(user);
        if (!code.equals(_code)) {
            throw new AosException("验证码不正确");
        }
        try {
            delete(user);
        } catch (Exception e) {
        }
    }

}
