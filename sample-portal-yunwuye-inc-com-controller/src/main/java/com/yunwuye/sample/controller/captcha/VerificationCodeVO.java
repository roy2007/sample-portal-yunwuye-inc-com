package com.yunwuye.sample.controller.captcha;

import com.yunwuye.sample.enums.VerificationCodeKeyEnum;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author Roy
 *
 * @date 2022年7月3日-上午10:07:36
 */
@ToString
@Data
public class VerificationCodeVO{

    /**
    * Verification code configuration
    */
    private VerificationCodeKeyEnum codeType;
    /**
    * The validity period of captcha minute
    */
    private Long                    expiration = 2L;
    /**
    * Content length of verification code
    */
    private int                     length     = 2;
    /**
    * Verification code width
    */
    private int                     width      = 111;
    /**
    * Captcha height
    */
    private int                     height     = 36;
    /**
    * Verification code font
    */
    private String                  fontName;
    /**
    * font size
    */
    private int                     fontSize   = 25;
    /**
    * Verification code prefix
    * @return
    */
    private String                  codeKey;

    public VerificationCodeKeyEnum getCodeType () {
        return codeType;
    }
}
