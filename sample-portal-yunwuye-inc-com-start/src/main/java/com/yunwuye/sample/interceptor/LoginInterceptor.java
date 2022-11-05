package com.yunwuye.sample.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.yunwuye.sample.security.jwt.TokenProvider;

/**
 *
 * @author Roy
 *
 * @date 2022年7月10日-下午3:45:45
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{

    Logger                logger = LoggerFactory.getLogger (LoginInterceptor.class);

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {
        boolean isSuccess = true;
        String token = request.getHeader (tokenProvider.getAuthorizeHeaderKey ());
        isSuccess = StringUtils.isNotBlank (token);
        if (StringUtils.isNotBlank (token)) {
            isSuccess = tokenProvider.validateToken (token);
        }
        // // 获取用户名,判断用户是否存在，或者冻结
        // Authentication authentication = SecurityContextHolder.getContext ().getAuthentication ();
        // // 判断用户是否存在，不存在就跳转到登录界面
        // if (authentication != null && StringUtils.isEmpty (authentication.getName ())) {
        // // 查库中用户是否存在，且未被冻结
        // }
        if (!isSuccess) {
            // response.setContentType ("text/html; charset=utf-8");
            // response.getWriter ().write ("请先登录！");
            // response.sendRedirect ("/index.html");
            // request.getRequestDispatcher ("/index.html").forward (request, response);
        }
        return isSuccess;
    }
}
