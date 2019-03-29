package com.disanbo.component.common.configuration.handler;

import com.disanbo.component.common.entity.ExceptionVO;
import com.disanbo.component.common.entity.ResponseVO;
import com.disanbo.component.common.enums.CommonResponseEnum;
import com.disanbo.component.common.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局错误处理
 *
 * @author chauncy
 * @date 2018/8/23 11:41
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseVO exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        ExceptionVO exceptionVO = new ExceptionVO();

        // 请求地址
        String url = request.getRequestURI();
        String query = request.getQueryString();
        if (StringUtils.isNotBlank(query)) {
            url += "?" + query;
        }
        // 请求方法
        String method = request.getMethod();

        exceptionVO.setUrl(url);
        exceptionVO.setMethod(method);
        // 不支持的请求方法
        if (e instanceof HttpRequestMethodNotSupportedException) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return new ResponseVO<>(CommonResponseEnum.exception_method_not_supported, exceptionVO);
        }

        // 请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> map = new HashMap<>(8);
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            map.put(header, request.getHeader(header));
        }
        // 请求参数
        String params;
        if (StringUtils.isNotBlank(request.getContentType()) && request.getContentType().contains("form-data")) {
            Enumeration<String> parameterNames = request.getParameterNames();
            Map<String, String> paramMap = new HashMap<>(8);
            while (parameterNames.hasMoreElements()) {
                String param = parameterNames.nextElement();
                paramMap.put(param, request.getParameter(param));
            }
            try {
                params = JSON.toJSONString(paramMap);
            } catch (Exception e1) {
                params = "";
            }
        } else {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        request.getInputStream()));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                params = URLDecoder.decode(sb.toString().trim(), StandardCharsets.UTF_8.name());
            } catch (Exception e1) {
                params = "";
            }
        }
        // 用户IP
        String ip = request.getRemoteAddr();
        // 错误信息
        String exception = e.getMessage();

        exceptionVO.setIp(ip);
        exceptionVO.setHeader(map);
        exceptionVO.setParams(params);
        exceptionVO.setException(exception);

        String header;
        try {
            header = JSON.toJSONString(map);
        } catch (Exception e1) {
            header = "";
        }
        log.error("发现未知的错误: \n\rip：{}\n\rurl：{}\n\rmethod：{}\n\rheader：{}\n\rparams：{}\n\rexception：{}", ip, url, method, header, params, e.toString());
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new ResponseVO<>(CommonResponseEnum.exception_unknown, exceptionVO);
    }
}
