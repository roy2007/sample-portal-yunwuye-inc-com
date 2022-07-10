package com.yunwuye.sample.controller.captcha;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wf.captcha.base.Captcha;
import com.yunwuye.sample.controller.BaseController;
import com.yunwuye.sample.enums.VerificationCodeKeyEnum;
import com.yunwuye.sample.util.RandomUtil;


/**
 *
 * @author Roy
 *
 * @date 2022年7月3日-上午10:17:59
 */
@RestController
@RequestMapping ("/verifcation")
public class CaptchaController extends BaseController{

    @Autowired
    private CaptchaUtil captchaUtil;

//    @Autowired
//    private RedisUtils  redisUtils;

    // @ApiOperation (value = " Get verification code ", notes = " Get verification code ")
    @GetMapping ("/code")
    public Object getCode () {
        Captcha captcha = captchaUtil.getCaptcha ();
        // When the verification code type is arithmetic Time and length >= 2 when ,captcha.text() There is a chance
        // that the result will be floating point
        String captchaValue = captcha.text ();
        if (captcha.getCharType () - 1 == VerificationCodeKeyEnum.ARITHMETIC.ordinal ()
                        && captchaValue.contains (".")) {
            captchaValue = captchaValue.split ("\\.")[0];
        }
        // preservation
        // Long expiration = captchaUtil.getVerificationCodeVO ().getExpiration ();
        String uuid = "code-key-" + RandomUtil.simpleUUID ();
        // redisUtils.set (uuid, captchaValue, expiration, TimeUnit.MINUTES);
        // Verification code information
        Map<String, Object> imgResult = new HashMap<String, Object> (2);
        imgResult.put ("img", captcha.toBase64 ());
        imgResult.put ("uuid", uuid);
        return imgResult;
    }
}
