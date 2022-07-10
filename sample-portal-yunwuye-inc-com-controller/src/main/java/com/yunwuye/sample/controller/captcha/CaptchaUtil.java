package com.yunwuye.sample.controller.captcha;

import java.awt.Font;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.ChineseGifCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.yunwuye.sample.enums.VerificationCodeKeyEnum;
import lombok.Data;

/**
 *
 * @author Roy
 *
 * @date 2022年7月3日-上午10:26:23
 */
@Data
public class CaptchaUtil{

    private VerificationCodeVO verificationCodeVO;

    /**
    * Get the verification code production class
    * @return
    */
    public Captcha getCaptcha () {
        if (Objects.isNull (verificationCodeVO)) {
            verificationCodeVO = new VerificationCodeVO ();
            if (Objects.isNull (verificationCodeVO.getCodeType ())) {
                verificationCodeVO.setCodeType (VerificationCodeKeyEnum.ARITHMETIC);
            }
        }
        return switchCaptcha (verificationCodeVO);
    }

    /**
    * Produce verification code according to configuration information
    * @param loginCode
    * @return
    */
    private Captcha switchCaptcha (VerificationCodeVO verificationCodeVO) {
        Captcha captcha = null;
        synchronized (this) {
            switch (verificationCodeVO.getCodeType ()) {
                case ARITHMETIC:
                    captcha = new FixedArithmeticCaptcha (verificationCodeVO.getWidth (),
                                    verificationCodeVO.getHeight ());
                    captcha.setLen (verificationCodeVO.getLength ());
                    break;
                case CHINESE:
                    captcha = new ChineseCaptcha (verificationCodeVO.getWidth (), verificationCodeVO.getHeight ());
                    captcha.setLen (verificationCodeVO.getLength ());
                    break;
                case CHINESE_GIF:
                    captcha = new ChineseGifCaptcha (verificationCodeVO.getWidth (), verificationCodeVO.getHeight ());
                    captcha.setLen (verificationCodeVO.getLength ());
                    break;
                case GIF:
                    captcha = new GifCaptcha (verificationCodeVO.getWidth (), verificationCodeVO.getHeight ());
                    captcha.setLen (verificationCodeVO.getLength ());
                    break;
                case SPEC:
                    captcha = new SpecCaptcha (verificationCodeVO.getWidth (), verificationCodeVO.getHeight ());
                    captcha.setLen (verificationCodeVO.getLength ());
                default:
                    System.out.println (
                                    " Verification code configuration information error ！ Configure view correctly VerificationCodeKeyEnum ");
            }
        }
        if (StringUtils.isNotBlank (verificationCodeVO.getFontName ())) {
            captcha.setFont (new Font (verificationCodeVO.getFontName (), Font.PLAIN,
                            verificationCodeVO.getFontSize ()));
        }
        return captcha;
    }

    static class FixedArithmeticCaptcha extends ArithmeticCaptcha{

        public FixedArithmeticCaptcha (int width, int height) {
            super (width, height);
        }

        @Override
        protected char[] alphas () {
            // Generate random numbers and operators
            int n1 = num (1, 10), n2 = num (1, 10);
            int opt = num (3);
            // The result of the calculation is
            int res = new int[] { n1 + n2, n1 - n2, n1 * n2 }[opt];
            // Convert to character operator
            char optChar = "+-x".charAt (opt);
            this.setArithmeticString (String.format ("%s%c%s=?", n1, optChar, n2));
            this.chars = String.valueOf (res);
            return chars.toCharArray ();
        }
    }
}
