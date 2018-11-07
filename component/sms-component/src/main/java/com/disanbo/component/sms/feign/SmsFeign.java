package com.disanbo.component.sms.feign;

import com.disanbo.component.common.entity.ResponseVO;
import com.disanbo.component.sms.entity.Message;
import com.disanbo.component.sms.feignfallback.SmsFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wangtao
 * @date 2018/9/19 11:34
 */
@FeignClient(name = "sms-base", path = "/api/sms", fallback = SmsFallBack.class)
public interface SmsFeign {

    /**
     * 发送短信
     */
    @PostMapping("/send")
    ResponseVO send(@RequestBody Message message);
}
