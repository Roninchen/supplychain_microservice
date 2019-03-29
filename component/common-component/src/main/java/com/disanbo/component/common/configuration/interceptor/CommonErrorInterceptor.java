package com.disanbo.component.common.configuration.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 默认错误拦截器
 *
 * @author chauncy
 * @date 2018/8/23 15:16
 */
@Component
public class CommonErrorInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 修改springBoot原本自带的/error，使其返回自定义json格式数据
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        request.getRequestDispatcher("/exception/url/not-fount").forward(request, response);
        return false;
    }
}
