package com.disanbo.component.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用响应枚举
 *
 * @author wangtao
 * @date 2018/10/18 19:07
 */
@Getter
@AllArgsConstructor
public enum CommonResponseEnum implements ResponseEnum {
    // 通用模块，请求错误或未知的错误
    success(200200, "success"),
    failure(200500, "failure"),
    exception_url_not_found(400404, "exception_url_not_found"),
    exception_request_body_null(400404, "exception_request_body_null"),
    exception_method_not_supported(400405, "exception_method_not_supported"),
    exception_feign(500500, "exception_feign"),
    exception_unknown(500500, "exception_unknown"),
    token_format(200302, "token_format"),
    token_binding(200303, "token_binding"),
    query_success(200200, "query_success"),
    demonstration_permission(200500, "demonstration_permission"),
    ;

    private Integer code;
    private String message;
}
