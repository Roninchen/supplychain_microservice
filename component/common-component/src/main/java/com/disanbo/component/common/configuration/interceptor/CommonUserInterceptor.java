package com.disanbo.component.common.configuration.interceptor;

import com.disanbo.component.common.constant.CommonConst;
import com.disanbo.component.common.entity.UserVO;
import com.disanbo.component.common.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author chauncy
 * @date 2018/10/8 15:31
 */
@Component
@Slf4j
public class CommonUserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jsonUser = request.getHeader(CommonConst.USER);
        if (StringUtils.isNotBlank(jsonUser)) {
            UserVO userVO = JSON.parseObject(jsonUser, UserVO.class);
            request.setAttribute(CommonConst.USER, userVO);
        }
        return true;
    }
}
