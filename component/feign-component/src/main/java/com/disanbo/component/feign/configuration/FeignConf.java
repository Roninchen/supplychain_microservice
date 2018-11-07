package com.disanbo.component.feign.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangtao
 * @date 2018/9/19 14:12
 */
@Configuration
@EnableFeignClients(basePackages = "com.disanbo")
public class FeignConf {

}
