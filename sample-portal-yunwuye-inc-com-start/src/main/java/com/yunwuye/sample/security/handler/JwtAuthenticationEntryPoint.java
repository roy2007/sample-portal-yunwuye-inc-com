package com.yunwuye.sample.security.handler;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

    private static final Logger LOG = LoggerFactory.getLogger (JwtAuthenticationEntryPoint.class);

    @Override
    public void commence (HttpServletRequest request,
                    HttpServletResponse response,
                    AuthenticationException authException) throws IOException {
        // This is invoked when user tries to access a secured REST resource without supplying any credentials
        // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
        // Here you can place any message you want
        LOG.debug ("Jwt 入口点身份验证失败", authException);
        response.sendError (HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage ());
    }
}
