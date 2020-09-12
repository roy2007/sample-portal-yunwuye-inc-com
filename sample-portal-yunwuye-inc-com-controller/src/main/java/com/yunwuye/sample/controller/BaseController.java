package com.yunwuye.sample.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Roy
 *
 * @date 2020年5月3日-下午10:53:15
 */
public class BaseController {

    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    protected String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }
}
