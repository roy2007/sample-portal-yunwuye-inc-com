package com.yunwuye.sample.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.DefaultCorsProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 *
 * @author Roy
 *
 * @date 2022年11月5日-上午11:55:07
 */
@Configuration
public class CustomizeCrossByRegular implements WebMvcRegistrations{

    private static final Logger               LOG                         = LoggerFactory
                    .getLogger (CustomizeCrossByRegular.class);
    private static final Map<String, Pattern> CACHE_CROSS_ORIGIN_REGX_MAP = new ConcurrentHashMap<> ();

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping () {
        RequestMappingHandlerMapping handler = new RequestMappingHandlerMapping ();
        handler.setCorsProcessor (new DefaultCorsProcessor (){

            @Override
            protected String checkOrigin (CorsConfiguration config, String requestOrigin) {
                LOG.info ("~~~~~~~~~~~~requestOrigin~~~~~~~~~~~~~{}", requestOrigin);
                String checkAfterRequestOrigin = super.checkOrigin (config, requestOrigin);
                if (StringUtils.isNotBlank (checkAfterRequestOrigin)) {
                    return checkAfterRequestOrigin;
                }
                // 获取当前服务中所有允许跨域请求的origins，同时对应正则表达式设置跨域也在这里获取到
                List<String> allowedOrigins = config.getAllowedOrigins ();
                if (CollectionUtils.isEmpty (allowedOrigins)) {
                    return null;
                }
                return postCheckOriginByRegx (allowedOrigins, requestOrigin);
            }

            private String postCheckOriginByRegx (List<String> allowedOrigins, String requestOrigin) {
                for (String allowedOrigin : allowedOrigins) {
                    if (StringUtils.isBlank (allowedOrigin)) {
                        continue;
                    }
                    LOG.info ("qq~~~~~~~~~~~~allowedOrigin~~~~~~~~~~~~~{}", allowedOrigin);
                    LOG.info ("qq~~~~~~~~~~~~requestOrigin~~~~~~~~~~~~~{}", requestOrigin);
                    Pattern pattern = CACHE_CROSS_ORIGIN_REGX_MAP.computeIfAbsent (allowedOrigin, Pattern::compile);
                    if (pattern.matcher (requestOrigin).matches ()) {
                        return requestOrigin;
                    }
                }
                return null;
            }
        });
        return handler;
    }
}
