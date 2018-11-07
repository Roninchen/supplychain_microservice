package com.disanbo.component.common.configuration.interceptor;

import com.disanbo.component.common.annotation.MustBeBound;
import com.disanbo.component.common.constant.CommonConst;
import com.disanbo.component.common.constant.LoginType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangtao
 * @date 2018/10/8 15:31
 */
@Component
@Slf4j
public class CommonBoundInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String loginType = request.getHeader(CommonConst.LOGIN_TYPE);
        if (LoginType.NOT_BINDING.equals(loginType)) {
            // 对未绑定用户不开放接口
            MustBeBound annotation = ((HandlerMethod) handler).getMethodAnnotation(MustBeBound.class);
            if (annotation != null) {
                String uri = request.getRequestURI();
                log.info("未绑定用户无法访问当前接口：{}", uri);
                request.getRequestDispatcher("/exception/token/binding").forward(request, response);
                return false;
            }
        }
        return true;
    }
}
