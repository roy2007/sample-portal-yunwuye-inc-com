package com.yunwuye.sample.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 *
 * @author Roy
 *
 * @date 2022年11月5日-下午8:20:29
 */
@Component
public class ErrorConfig implements ErrorPageRegistrar{

    Logger logger = LoggerFactory.getLogger (ErrorConfig.class);
    @Override
    public void registerErrorPages (ErrorPageRegistry registry) {
        logger.info ("错误跳转页!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        ErrorPage err404Page = new ErrorPage (HttpStatus.NOT_FOUND, "/index");
        ErrorPage err500Page = new ErrorPage (HttpStatus.INTERNAL_SERVER_ERROR, "/index");
        ErrorPage err401Page = new ErrorPage (HttpStatus.NOT_FOUND, "/index");
        registry.addErrorPages (err404Page, err500Page, err401Page);
    }
}
