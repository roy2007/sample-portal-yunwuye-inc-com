package com.yunwuye.sample.aop;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 需要启用 ErrorMvcAutoConfiguration.class才能生效，在App.java 启动时如果exclude，这里不生效.
 * 此时，可以采用JwtAccessDeniedHandler 来处理，详细请看JwtAccessDeniedHandler.java
 * 这种方式不够严谨，还是用JwtAuthenticationEntryPoint\JwtAccessDeniedHandler 方式更为符合http要求
 *
 */
@Deprecated
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public String handleAccessDeniedException(AccessDeniedException ex) {
        // 返回错误信息给客户端
        return "Access is denied: " + ex.getMessage();
    }
}
