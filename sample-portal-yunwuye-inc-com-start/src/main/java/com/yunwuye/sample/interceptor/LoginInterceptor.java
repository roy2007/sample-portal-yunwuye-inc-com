package com.yunwuye.sample.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.alibaba.druid.util.StringUtils;

/**
 *
 * @author Roy
 *
 * @date 2022年7月10日-下午3:45:45
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{

    Logger logger = LoggerFactory.getLogger (LoginInterceptor.class);

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {
        // 获取用户名
        Authentication authentication = SecurityContextHolder.getContext ().getAuthentication ();
        // 判断用户是否存在，不存在就跳转到登录界面
        if (authentication == null || StringUtils.isEmpty (authentication.getName ())) {
            response.sendRedirect (request.getContextPath () + "/index.html");
            return false;
        }
        return true;
    }
}
