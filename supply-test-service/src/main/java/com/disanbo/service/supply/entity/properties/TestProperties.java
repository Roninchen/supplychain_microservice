package com.disanbo.service.supply.entity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chauncy
 * @date 2018/11/6 17:06
 */
@Data
@Component
@ConfigurationProperties(prefix = "test")
public class TestProperties {
    private String name;
    private Integer age;
    private Boolean isBoy;
}
