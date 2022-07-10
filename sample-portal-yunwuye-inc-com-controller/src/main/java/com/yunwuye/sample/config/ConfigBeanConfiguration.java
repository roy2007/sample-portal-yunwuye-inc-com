package com.yunwuye.sample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.yunwuye.sample.controller.captcha.CaptchaUtil;

/**
 *
 * @author Roy
 *
 * @date 2022年7月3日-上午10:28:12
 */
@Configuration
public class ConfigBeanConfiguration{

    @Bean
    @ConfigurationProperties (prefix = "login")
    public CaptchaUtil loginProperties () {
        return new CaptchaUtil ();
    }
}
