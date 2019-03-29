package com.disanbo.component.common.configuration.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 预请求拦截器
 *
 * @author chauncy
 * @date 2018/10/8 15:31
 */
@Component
public class CommonOptionsInterceptor implements HandlerInterceptor {
    private final static String OPTIONS = "options";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 预请求处理
        if (OPTIONS.equalsIgnoreCase(request.getMethod())) {
            // 此处可以做一些校验等
            response.setStatus(HttpServletResponse.SC_OK);
            return false;
        }
        return true;
    }
}
