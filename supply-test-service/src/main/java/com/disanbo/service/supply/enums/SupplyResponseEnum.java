package com.disanbo.service.supply.enums;

import com.disanbo.component.common.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用以描述响应的信息
 *
 * @author chauncy
 * @date 2018/4/16
 */
@Getter
@AllArgsConstructor
public enum SupplyResponseEnum implements ResponseEnum {
    // 令牌
    test_test(200200, "test_test"),

    ;


    private final Integer code;
    private final String message;
}
