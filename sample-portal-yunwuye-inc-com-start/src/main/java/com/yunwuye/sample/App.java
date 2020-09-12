package com.yunwuye.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.yunwuye.sample.aop.AspectControllerExceptionAOP;

/**
 *
 *
 * @author Roy
 *
 * @date 2020年5月3日-下午10:19:20
 */
@EnableDubboConfiguration
@ConfigurationProperties(prefix = "dubbo.application")
@SpringBootApplication(scanBasePackages = { "com.yunwuye.sample" })
public class App {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        ConfigurableApplicationContext appContext = app.run(args);
        System.out.println(appContext.getBean(AspectControllerExceptionAOP.class));
        appContext.close();
         SpringApplication.run(App.class, args);
    }
}
