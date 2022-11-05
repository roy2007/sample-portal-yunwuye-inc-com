package com.yunwuye.sample.security.handler;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler{

    private static final Logger LOG = LoggerFactory.getLogger (JwtAccessDeniedHandler.class);

    @Override
    public void handle (HttpServletRequest request, HttpServletResponse response,
                    AccessDeniedException accessDeniedException) throws IOException {
        // This is invoked when user tries to access a secured REST resource without the necessary authorization
        // We should just send a 403 Forbidden response because there is no 'error' page to redirect to
        // Here you can place any message you want
        LOG.debug ("访问初拒绝了！", accessDeniedException);
        response.sendError (HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage ());
    }
}
