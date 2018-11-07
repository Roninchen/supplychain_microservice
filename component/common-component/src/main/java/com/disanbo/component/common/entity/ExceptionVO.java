package com.disanbo.component.common.entity;

import lombok.Data;

import java.util.Map;

/**
 * 异常类
 *
 * @author wangtao
 * @date 2018/8/23 16:31
 */
@Data
public class ExceptionVO {
    private String ip;
    private String url;
    private String method;
    private Map header;
    private String params;
    private String exception;
}
