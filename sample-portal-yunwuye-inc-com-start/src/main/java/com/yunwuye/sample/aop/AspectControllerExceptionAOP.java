package com.yunwuye.sample.aop;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.yunwuye.sample.common.base.enums.CommonResultCode;
import com.yunwuye.sample.common.base.exception.CommonException;
import com.yunwuye.sample.common.base.result.PageResult;
import com.yunwuye.sample.common.base.result.Result;
import com.yunwuye.sample.common.util.ResultUtil;

@Aspect
@Component
public class AspectControllerExceptionAOP {
    private static final Logger logger = LoggerFactory.getLogger(AspectControllerExceptionAOP.class);

    @Pointcut(value = "execution(public com.yunwuye.sample.common.base.result.PageResult *(..))")
    public void handlerPageResultMethod() {
    }

    @Pointcut(value = "execution(public com.yunwuye.sample.common.base.result.Result *(..))")
    public void handlerResultMethod() {
    }

    /**
     * Controller 中方法返回类型是Result<?>的统一拦截异常处理
     *
     * @param pjp
     * @return
     */
    @Around(value = "handlerPageResultMethod()")
    public Object handlPageResultMethod(ProceedingJoinPoint pjp) {
        PageResult<?> result = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String inputParam = converParamToJSONString(pjp);
        try {
            Long startTime = System.currentTimeMillis();
            result = (PageResult<?>) pjp.proceed();
            Long elapses = System.currentTimeMillis() - startTime;
            String dataString = JSON.toJSONString(result.getData());
            String extString = JSON.toJSONString(result.getExtMap());
            Object[] args = { request.getRequestURI(), pjp.getSignature(), inputParam, result.getCode(),
                    result.getMessage(), dataString, extString, elapses };
            makeDebugLog(args);
        } catch (Throwable t) {
            result = new PageResult<>();
            Result<?> failResult = this.handlerException(pjp, t, request, inputParam);
            BeanUtils.copyProperties(failResult, result);
        }
        return result;
    }

    /**
     * Controller 中方法返回类型是Result<?>的统一拦截异常处理
     *
     * @param pjp
     * @return
     */
    @Around(value = "handlerResultMethod()")
    public Object handlerResultMethod(ProceedingJoinPoint pjp) {
        Result<?> result = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String inputParam = converParamToJSONString(pjp);
        try {
            Long startTime = System.currentTimeMillis();
            result = (Result<?>) pjp.proceed();
            Long elapses = System.currentTimeMillis() - startTime;
            String dataString = JSON.toJSONString(result.getData());
            String extString = JSON.toJSONString(result.getExtMap());
            Object[] args = { request.getRequestURI(), pjp.getSignature(), inputParam, result.getCode(),
                    result.getMessage(), dataString, extString, elapses };
            makeDebugLog(args);
        } catch (Throwable t) {
            result = new Result<>();
            Result<?> failResult = this.handlerException(pjp, t, request, inputParam);
            BeanUtils.copyProperties(failResult, result);
        }
        return result;
    }

    /**
     * 将请求参数转换成JSONString
     *
     * @param pjp
     * @return
     */
    private String converParamToJSONString(ProceedingJoinPoint pjp) {
        String argsJSONString = "";
        Object[] args = pjp.getArgs();
        Object[] arguments = new Object[args.length];
        int index = 0;
        for (Object arg : args) {
            if (arg == null || arg instanceof ServletRequest || arg instanceof ServletResponse
                    || arg instanceof MultipartFile) {
                continue;
            }
            arguments[index] = arg;
            index++;
        }
        try {
            argsJSONString = JSON.toJSONString(arguments);
        } catch (Exception e) {
            logger.error("conver param to JSONString error= {0}", e.getMessage());
            argsJSONString = arguments.toString();
        }
        return argsJSONString;
    }

    /**
     *
     * @param args
     */
    private void makeDebugLog(Object... args) {
        logger.debug("uri:{} | method:{} | param:{} | code:{} | message:{} | data:{} |extMap:{} | elapses:{} ms", args);
    }

    /**
    *
    * @param args
    */
   private void makeErrorLog(Object... args) {
       logger.debug("uri:{} | method:{} | param:{} | error:{}", args);
   }

    private Result<?> handlerException(ProceedingJoinPoint pjp, Throwable t, HttpServletRequest request,
            String inputParam) {
        Result<?> failResult = ResultUtil.createFailResult(CommonResultCode.BIZ_FAIL);

        Object[] args = { request.getRequestURI(), pjp.getSignature(), inputParam, t.getMessage() };
        makeErrorLog(args);

        if(t instanceof CommonException ){
            CommonException bizBaseException = (CommonException)t;
            failResult.setCode(bizBaseException.code.getCode());
            failResult.setMessage(bizBaseException.getMessage());
            return failResult;
        }
        // TODO add judgment of preset anomalies

        return failResult;
    }
}
