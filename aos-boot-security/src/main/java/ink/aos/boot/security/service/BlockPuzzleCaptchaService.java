package ink.aos.boot.security.service;

import ink.aos.module.captcha.api.BlockPuzzleCaptcha;
import ink.aos.module.captcha.api.Captcha;
import ink.aos.module.captcha.api.CaptchaCheck;
import ink.aos.module.captcha.exception.AosCaptchaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class BlockPuzzleCaptchaService {

    @Autowired
    private BlockPuzzleCaptcha blockPuzzleCaptcha;

    public Captcha get() throws AosCaptchaException {
        return blockPuzzleCaptcha.get();
    }

    public void check(CaptchaCheck captchaCheck) throws AosCaptchaException {
        //取坐标信息
        blockPuzzleCaptcha.check(captchaCheck);
    }

    public void verification(String token) throws AosCaptchaException {
        blockPuzzleCaptcha.verification(token);
    }

}
